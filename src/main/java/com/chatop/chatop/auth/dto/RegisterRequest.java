package com.chatop.chatop.auth.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
