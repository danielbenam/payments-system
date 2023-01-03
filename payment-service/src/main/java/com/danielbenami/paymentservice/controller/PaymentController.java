package com.danielbenami.paymentservice.controller;



import com.danielbenami.commons.dto.PaymentRequestDto;
import com.danielbenami.commons.dto.PaymentResponseDto;
import com.danielbenami.paymentservice.entity.Payment;
import com.danielbenami.paymentservice.exception.PaymentNotFoundException;
import com.danielbenami.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public PaymentResponseDto createPayment(@Valid @RequestBody PaymentRequestDto paymentRequestDto){
        Payment payment = paymentService.createPayment(paymentRequestDto);
        return convertEntityToDto(payment);
    }

    @GetMapping("/all")
    public List<PaymentResponseDto> getPayments(){
        return paymentService.getAll().stream().map(this::convertEntityToDto).collect(toList());
    }

    @GetMapping("/{id}")
    public PaymentResponseDto getPayment(@PathVariable int id){
        return paymentService.getPayment(id).map(this::convertEntityToDto)
                .orElseThrow(() -> new PaymentNotFoundException("payment not found with id : " + id));
    }

    private PaymentResponseDto convertEntityToDto(Payment payment) {
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setPaymentId(payment.getId());
        paymentResponseDto.setUserId(payment.getUserId());
        paymentResponseDto.setPayeeId(payment.getPayeeId());
        paymentResponseDto.setCurrency(payment.getCurrency());
        paymentResponseDto.setAmount(payment.getAmount());
        paymentResponseDto.setPaymentMethodId(payment.getPaymentMethodId());
        paymentResponseDto.setPaymentStatus(payment.getPaymentStatus());
        return paymentResponseDto;
    }


}
