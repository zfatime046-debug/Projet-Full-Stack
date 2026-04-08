package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PhaseDTO {

    private Long id;
    private String code;
    private String libelle;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double montant;

    private String etatRealisationLabel;
    private String etatFacturationLabel;
    private String etatPaiementLabel;

    private Long projetId;
}