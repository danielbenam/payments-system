package com.danielbenami.paymentservice.service;

import com.danielbenami.commons.dto.PaymentRequestDto;
import com.danielbenami.commons.event.PaymentStatus;
import com.danielbenami.paymentservice.entity.Payment;
import com.danielbenami.paymentservice.entity.PaymentMethod;
import com.danielbenami.paymentservice.entity.User;
import com.danielbenami.paymentservice.exception.CurrencyNotFoundException;
import com.danielbenami.paymentservice.exception.PaymentMethodNotFoundException;
import com.danielbenami.paymentservice.exception.UserNotFoundException;
import com.danielbenami.paymentservice.repository.PaymentRepository;
import com.danielbenami.paymentservice.utils.MockDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private static Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private List<String> currencies;

    private final UserService userService;
    private final PaymentRepository paymentRepository;

    private final PaymentStatusPublisher paymentStatusPublisher;

    @Value("${mock-data.currency-codes.file-path}")
    private String currencyCodesDataFilePath;

    @Autowired
    public PaymentService(UserService userService, PaymentRepository paymentRepository, PaymentStatusPublisher paymentStatusPublisher) {
        this.userService = userService;
        this.paymentRepository = paymentRepository;
        this.paymentStatusPublisher = paymentStatusPublisher;
    }

    @PostConstruct
    private void init() {
        this.currencies = MockDataReader.readModelEntities(currencyCodesDataFilePath, String.class);
        logger.info("Payment service was started successfully.");
    }
    @Transactional
    public Payment  createPayment(PaymentRequestDto paymentRequestDto) {
        validatePayment(paymentRequestDto);
        Payment payment = paymentRepository.save(convertDtoToEntity(paymentRequestDto));
        paymentRequestDto.setPaymentId(payment.getId());
        // produce kafka event with status PAYMENT_COMPLETED
        paymentStatusPublisher.publishPaymentEvent(paymentRequestDto,PaymentStatus.PAYMENT_CREATED);
        return payment;
    }

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPayment(int id) {
        return paymentRepository.findById(id);
    }

    private void validatePayment(PaymentRequestDto paymentRequestDto) {

        if (!currencies.contains(paymentRequestDto.getCurrency())) {
            throw new CurrencyNotFoundException("Currency code: " + paymentRequestDto.getCurrency() + " is not recognized.");
        }

        if (userService.getUserById(paymentRequestDto.getPayeeId()).isEmpty()) {
            throw new UserNotFoundException("Payee user-Id: " + paymentRequestDto.getPayeeId() + " was not found");
        }

        User payer = userService.getUserById(paymentRequestDto.getUserId()).
                orElseThrow(() -> new UserNotFoundException("Payer user-Id: " + paymentRequestDto.getUserId() + " was not found"));

        List<PaymentMethod> payerPaymentMethods = userService.getUserPaymentMethods(payer.getUserId());

        boolean validPaymentMethod = payerPaymentMethods.stream()
                .map(PaymentMethod::getPaymentMethodId)
                .anyMatch(pm -> paymentRequestDto.getPaymentMethodId().equals(pm));

        if (!validPaymentMethod) {
            throw new PaymentMethodNotFoundException("Payment method id: " + paymentRequestDto.getPaymentMethodId() + " was not found");
        }
    }


    private Payment convertDtoToEntity(PaymentRequestDto paymentRequestDto) {
        Payment payment = new Payment();
        payment.setUserId(paymentRequestDto.getUserId());
        payment.setPayeeId(paymentRequestDto.getPayeeId());
        payment.setCurrency(paymentRequestDto.getCurrency());
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setPaymentMethodId(paymentRequestDto.getPaymentMethodId());
        payment.setPaymentStatus(PaymentStatus.PAYMENT_CREATED);
        return payment;
    }


}
