package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PhaseRequest {

    private String code;
    private String libelle;
    private String description;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private double montant;

    private boolean etatRealisation;
    private boolean etatFacturation;
    private boolean etatPaiement;

    private Long projetId;
}