package com.danielbenami.paymentservice.entity;

import lombok.Data;

@Data
public class User {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;


}
