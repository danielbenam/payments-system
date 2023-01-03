package com.danielbenami.commons.event;

import com.danielbenami.commons.dto.PaymentRequestDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class PaymentEvent implements Event  {

    private UUID eventId;
    private LocalDateTime eventDate;
    private PaymentRequestDto paymentRequestDto;
    private  PaymentStatus paymentStatus;

    public PaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus) {
        eventId = UUID.randomUUID();
        eventDate = LocalDateTime.now();
        this.paymentRequestDto = paymentRequestDto;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public LocalDateTime getEventDate() {
        return eventDate;
    }
}
