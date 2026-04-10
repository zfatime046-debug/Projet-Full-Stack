package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrganismeRequestDTO {

    @NotBlank(message = "Le code est obligatoire")
    private String code;

    @NotBlank(message = "Le nom est obligatoire")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Ce champ ne doit contenir que des lettres")
    private String nom;

    private String adresse;

    @Pattern(regexp = "^[0-9+\\s]{8,15}$", message = "Numéro de téléphone invalide")
    private String telephone;

    private String nomContact;

    @Email(message = "Format email invalide")
    private String emailContact;

    private String siteWeb;
}