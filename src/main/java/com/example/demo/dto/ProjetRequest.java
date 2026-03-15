package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjetRequest {

    @NotBlank(message = "Le code est obligatoire")
    private String code;

    @NotBlank(message = "Le nom est obligatoire")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Ce champ ne doit contenir que des lettres")
    private String nom;

    private String description;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @Positive(message = "Le montant doit être positif")
    private double montant;

    @NotNull(message = "L'organisme est obligatoire")
    private Long organismeId;

    @NotNull(message = "Le chef de projet est obligatoire")
    private Long chefDeProjetId;
}