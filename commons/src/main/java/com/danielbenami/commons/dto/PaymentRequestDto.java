package com.danielbenami.commons.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequestDto {

    private Integer paymentId;
    @Min(0)
    private double amount;
    @NotBlank(message = "currency shouldn't be empty")
    private String currency;
    @NotBlank(message = "userId shouldn't be empty")
    private String userId;
    @NotBlank(message = "payeeId shouldn't be empty")
    private String payeeId;
    @NotBlank(message = "paymentMethodId shouldn't be empty")
    private String paymentMethodId;

}
