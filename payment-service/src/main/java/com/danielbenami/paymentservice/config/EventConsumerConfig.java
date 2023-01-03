package com.danielbenami.paymentservice.config;

import com.danielbenami.commons.event.RiskEngineEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {
    @Autowired
    private PaymentStatusUpdateHandler paymentStatusUpdateHandler;
    @Bean
    public Consumer<RiskEngineEvent> riskEngineEventConsumer(){
        return riskEngineEvent -> {
            paymentStatusUpdateHandler.updatePayment(riskEngineEvent.getRiskEngineRequestDto().getPaymentId(),
                    riskEngineEvent.getRiskEnginePaymentStatus());
        };
    }
}
