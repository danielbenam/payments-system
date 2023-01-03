package com.danielbenami.riskengine.config;

import com.danielbenami.commons.event.PaymentEvent;
import com.danielbenami.commons.event.RiskEngineEvent;
import com.danielbenami.riskengine.service.RiskEngineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class RiskEngineConsumerConfig {


    @Autowired
    private RiskEngineService service;

    @Bean
    public Function<Flux<PaymentEvent>, Flux<RiskEngineEvent>> riskAnalysisProcessor() {
        return flux -> flux.flatMap(this::analyzePayment);
    }

    private Mono<RiskEngineEvent> analyzePayment(PaymentEvent paymentEvent) {
            return Mono.fromSupplier(() -> service.newPaymentEvent(paymentEvent));
    }
}