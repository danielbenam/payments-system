package com.danielbenami.paymentservice.entity;

import lombok.Data;

@Data
public class PaymentMethod {

    private String paymentMethodId;
    private PaymentMethodType type;
    private String name;
    private int last4;
    private String userId;
}
