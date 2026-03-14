package com.example.demo.mappers;

import com.example.demo.dto.PhaseRequest;
import com.example.demo.entities.Phase;

public class PhaseMapper {

    public static Phase toEntity(PhaseRequest dto) {

        Phase phase = new Phase();

        phase.setCode(dto.getCode());
        phase.setLibelle(dto.getLibelle());
        phase.setDescription(dto.getDescription());
        phase.setDateDebut(dto.getDateDebut());
        phase.setDateFin(dto.getDateFin());
        phase.setMontant(dto.getMontant());
        phase.setEtatRealisation(String.valueOf(dto.isEtatRealisation()));
        phase.setEtatFacturation(String.valueOf(dto.isEtatFacturation()));
        phase.setEtatPaiement(String.valueOf(dto.isEtatPaiement()));

        return phase;
    }
}