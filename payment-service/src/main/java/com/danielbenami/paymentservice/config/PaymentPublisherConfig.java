package com.danielbenami.paymentservice.config;

import com.danielbenami.commons.event.PaymentEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class PaymentPublisherConfig {

    @Bean
    public Sinks.Many<PaymentEvent> paymentSink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<PaymentEvent>> paymentSupplier(Sinks.Many<PaymentEvent> sink){
        return sink::asFlux;
    }
}
