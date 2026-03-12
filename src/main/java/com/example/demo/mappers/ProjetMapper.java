package com.example.demo.mappers;

import com.example.demo.dto.ProjetRequest;
import com.example.demo.entities.Projet;

public class ProjetMapper {

    public static Projet toEntity(ProjetRequest dto) {

        Projet projet = new Projet();

        projet.setCode(dto.getCode());
        projet.setNom(dto.getNom());
        projet.setDescription(dto.getDescription());
        projet.setDateDebut(dto.getDateDebut());
        projet.setDateFin(dto.getDateFin());
        projet.setMontant(dto.getMontant());

        return projet;
    }
}