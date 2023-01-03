package com.danielbenami.paymentservice.exception;

public class PaymentMethodNotFoundException extends RuntimeException{

    public PaymentMethodNotFoundException (String message) {
        super(message);
    }
}
