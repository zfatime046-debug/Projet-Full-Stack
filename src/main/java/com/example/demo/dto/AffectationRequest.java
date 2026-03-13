package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AffectationRequest {

    private Long employeId;
    private Long phaseId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
}