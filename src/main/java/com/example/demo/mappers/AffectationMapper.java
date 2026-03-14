package com.example.demo.mappers;

import com.example.demo.dto.AffectationRequest;
import com.example.demo.entities.Affectation;

public class AffectationMapper {

    public static Affectation toEntity(AffectationRequest dto) {
        Affectation affectation = new Affectation();
        affectation.setDateDebut(dto.getDateDebut());
        affectation.setDateFin(dto.getDateFin());
        return affectation;
    }
}