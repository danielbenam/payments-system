package com.danielbenami.paymentservice.service;

import com.danielbenami.paymentservice.entity.PaymentMethod;
import com.danielbenami.paymentservice.entity.User;
import com.danielbenami.paymentservice.utils.MockDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${mock-data.users.file-path}")
    private String usersMockDataFilePath;

    @Value("${mock-data.payment-methods.file-path}")
    private String paymentMethodsMockDataFilePath;

    private Map<String, User> users;
    private Map<String, List<PaymentMethod>> paymentMethods;

    @PostConstruct
    private void init() {
        this.users = MockDataReader.readModelEntities(usersMockDataFilePath, User.class)
                .stream()
                .collect(Collectors.toMap(User::getUserId, Function.identity()));

        this.paymentMethods = MockDataReader.readModelEntities(paymentMethodsMockDataFilePath, PaymentMethod.class)
                .stream()
                .collect(Collectors.groupingBy(PaymentMethod::getUserId));
    }



    public Optional<User> getUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }


    public Optional<User> getUserByEmail(String email) {
        return users.values().stream().filter(u -> email.equals(u.getEmail())).findAny();
    }


    public List<User> getAllUsers() {
        return users.values().stream().collect(Collectors.toList());
    }

    public List<PaymentMethod> getUserPaymentMethods(String userId) {
        return paymentMethods.get(userId);
    }
}
