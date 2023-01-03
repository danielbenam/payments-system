package com.danielbenami.paymentservice.entity;

import com.danielbenami.commons.event.PaymentStatus;
import com.danielbenami.commons.event.RiskEnginePaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private double amount;

    private String currency;

    private String userId;

    private String payeeId;

    private String paymentMethodId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private RiskEnginePaymentStatus riskEnginePaymentStatus;


}
