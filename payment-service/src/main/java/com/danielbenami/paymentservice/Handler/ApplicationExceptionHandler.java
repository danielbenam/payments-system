package com.danielbenami.paymentservice.Handler;

import com.danielbenami.paymentservice.exception.PaymentMethodNotFoundException;
import com.danielbenami.paymentservice.exception.PaymentNotFoundException;
import com.danielbenami.paymentservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleInvalidArgument(MethodArgumentNotValidException ex){
        ApiError errorMessage = new ApiError(400, ex.getMessage());
        Map<String,String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->
                errorMap.put(error.getField(), error.getDefaultMessage()));

        errorMessage.setValidationErrors(errorMap);
        return errorMessage;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({PaymentNotFoundException.class,UserNotFoundException.class, PaymentMethodNotFoundException.class})
    public ApiError handlePaymentNotFoundException(Exception ex) {
        ApiError errorMessage = new ApiError(400, ex.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        errorMessage.setValidationErrors(errorMap);
        return errorMessage;
    }

}
