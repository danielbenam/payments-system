package com.danielbenami.paymentservice.config;

import com.danielbenami.commons.event.PaymentStatus;
import com.danielbenami.commons.event.RiskEnginePaymentStatus;
import com.danielbenami.paymentservice.entity.Payment;
import com.danielbenami.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class PaymentStatusUpdateHandler {

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public void updatePayment(int id, RiskEnginePaymentStatus riskEnginePaymentStatus){
       paymentRepository.findById(id)
                .ifPresent(payment -> {
                    payment.setRiskEnginePaymentStatus(riskEnginePaymentStatus);
                    updatePayment(payment);
                });
    }

    private void updatePayment(Payment payment) {
        var isComplete = RiskEnginePaymentStatus.PAYMENT_APPROVED.equals(payment.getRiskEnginePaymentStatus());
        var paymentStatus = isComplete ? PaymentStatus.PAYMENT_COMPLETED : PaymentStatus.PAYMENT_CANCELED;
        payment.setPaymentStatus(paymentStatus);
    }

}
