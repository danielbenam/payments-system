package com.danielbenami.paymentservice;

import com.danielbenami.paymentservice.entity.Payment;
import com.danielbenami.paymentservice.entity.User;
import com.danielbenami.paymentservice.repository.PaymentRepository;
import com.danielbenami.paymentservice.utils.MockDataReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PaymentServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}


}
