package com.danielbenami.paymentservice.service;

import com.danielbenami.commons.dto.PaymentRequestDto;
import com.danielbenami.commons.event.PaymentEvent;
import com.danielbenami.commons.event.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class PaymentStatusPublisher {

      @Autowired
      private Sinks.Many<PaymentEvent> paymentSink;

      public void publishPaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus) {
            PaymentEvent paymentEvent = new PaymentEvent(paymentRequestDto,paymentStatus);
            paymentSink.tryEmitNext(paymentEvent);

      }
}
