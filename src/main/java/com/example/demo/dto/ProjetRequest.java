package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjetRequest {

    private String code;
    private String nom;
    private String description;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private double montant;

    private Long organismeId;
    private Long chefDeProjetId;
}