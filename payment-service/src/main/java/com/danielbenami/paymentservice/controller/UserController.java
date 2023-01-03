package com.danielbenami.paymentservice.controller;

import com.danielbenami.paymentservice.entity.PaymentMethod;
import com.danielbenami.paymentservice.entity.User;
import com.danielbenami.paymentservice.exception.UserNotFoundException;
import com.danielbenami.paymentservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String userId) {
        if (userId==null) {
            throw new IllegalArgumentException("must provide a user-id");
        }
        return userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with id: " + userId + " was not found"));
    }

    @GetMapping("/all")
    public List<User> getUsers(){
        return userService.getAllUsers();

    }

    @GetMapping("/{id}/payment-methods")
    public List<PaymentMethod> getUserPaymentMethods(@PathVariable("id") String userId){
        if (userId==null) {
            throw new IllegalArgumentException("must provide a user-id");
        }
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with id: " + userId + " was not found"));
        return userService.getUserPaymentMethods(user.getUserId());
    }

    @GetMapping("/emails/{email}")
    public User getUserByEmail(@PathVariable("email") String email){
        if (email==null) {
            throw new IllegalArgumentException("must provide an email");
        }
        return userService.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user with email: " + email + " was not found"));

    }






}
