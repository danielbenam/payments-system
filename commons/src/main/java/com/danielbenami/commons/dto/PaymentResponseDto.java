package com.danielbenami.commons.dto;

import com.danielbenami.commons.event.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponseDto {

    private Integer paymentId;

    private Double amount;

    private String currency;

    private String userId;

    private String payeeId;

    private String paymentMethodId;

    private PaymentStatus paymentStatus;




}

