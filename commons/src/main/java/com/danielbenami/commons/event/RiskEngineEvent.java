package com.danielbenami.commons.event;

import com.danielbenami.commons.dto.RiskEngineRequestDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class RiskEngineEvent implements Event {

    private UUID eventId;
    private LocalDateTime eventDate;
    private RiskEngineRequestDto riskEngineRequestDto;
    private RiskEnginePaymentStatus riskEnginePaymentStatus;

    public RiskEngineEvent(RiskEngineRequestDto riskEngineRequestDto, RiskEnginePaymentStatus riskEnginePaymentStatus) {
        eventId = UUID.randomUUID();
        eventDate = LocalDateTime.now();
        this.riskEngineRequestDto = riskEngineRequestDto;
        this.riskEnginePaymentStatus = riskEnginePaymentStatus;
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
