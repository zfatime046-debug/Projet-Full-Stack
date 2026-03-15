package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeRequestDTO {

    @NotBlank(message = "Le matricule est obligatoire")
    private String matricule;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Pattern(regexp = "^[0-9+\\s]{8,15}$", message = "Numéro de téléphone invalide")
    private String telephone;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format email invalide")
    private String email;

    @NotBlank(message = "Le login est obligatoire")
    @Size(min = 3, message = "Login trop court (min 3 caractères)")
    private String login;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Mot de passe trop court (min 6 caractères)")
    private String password;

    @NotNull(message = "Le profil est obligatoire")
    private Long profilId;
}