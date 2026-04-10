package com.example.demo.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthMeDTO {

    private Long id;
    private String login;
    private String email;
    private String role;
}