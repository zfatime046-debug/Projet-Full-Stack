package com.example.demo.dto;

import lombok.Data;

@Data
public class EmployeRequestDTO {
    private String matricule;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String login;
    private String password;
    private Long profilId;
}