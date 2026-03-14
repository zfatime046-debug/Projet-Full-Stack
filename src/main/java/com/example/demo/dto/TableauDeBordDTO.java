package com.example.demo.dto;

import lombok.Data;

@Data
public class TableauDeBordDTO {

    private int nombreProjetsEnCours;
    private int nombreProjetsClotures;
    private int nombrePhasesTermineesNonFacturees;
    private int nombrePhasesFactureesNonPayees;
    private int nombrePhasesPayees;
}