package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AffectationRequest {

    @NotNull(message = "L'employé est obligatoire")
    private Long employeId;

    @NotNull(message = "La phase est obligatoire")
    private Long phaseId;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;
}