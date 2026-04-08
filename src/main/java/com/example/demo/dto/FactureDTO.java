package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FactureDTO {

    private Long id;
    private String code;
    private LocalDate dateFacture;
    private BigDecimal montant;

    private String statut;

    private Long phaseId;
    private String phaseNom;

    private String etatFacturationLabel;
    private String etatPaiementLabel;
    private String etatRealisationLabel;
}