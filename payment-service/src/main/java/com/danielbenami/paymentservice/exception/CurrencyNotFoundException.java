package com.danielbenami.paymentservice.exception;

public class CurrencyNotFoundException extends RuntimeException{

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
