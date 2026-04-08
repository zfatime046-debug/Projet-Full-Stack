package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PhaseRequest {

    @NotBlank(message = "Le code est obligatoire")
    private String code;

    @NotBlank(message = "Le libellé est obligatoire")
    private String libelle;

    private String description;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @Positive(message = "Le montant doit être positif")
    private double montant;

    private Boolean etatRealisation = false;
    private Boolean etatFacturation = false;
    private Boolean etatPaiement = false;

    @NotNull(message = "Le projet est obligatoire")
    private Long projetId;
}