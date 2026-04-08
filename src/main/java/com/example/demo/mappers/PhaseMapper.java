package com.example.demo.mappers;

import com.example.demo.dto.PhaseRequest;
import com.example.demo.dto.PhaseDTO;
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

        phase.setEtatRealisation(dto.getEtatRealisation());
        phase.setEtatFacturation(dto.getEtatFacturation());
        phase.setEtatPaiement(dto.getEtatPaiement());

        return phase;
    }

    public static PhaseDTO toDTO(Phase phase) {
        PhaseDTO dto = new PhaseDTO();

        dto.setId(phase.getId());
        dto.setCode(phase.getCode());
        dto.setLibelle(phase.getLibelle());
        dto.setDescription(phase.getDescription());
        dto.setDateDebut(phase.getDateDebut());
        dto.setDateFin(phase.getDateFin());
        dto.setMontant(phase.getMontant());

        if (phase.getProjet() != null) {
            dto.setProjetId(phase.getProjet().getId());
        }

        dto.setEtatRealisationLabel(
                Boolean.TRUE.equals(phase.getEtatRealisation()) ? "terminée" : "non terminée"
        );

        dto.setEtatFacturationLabel(
                Boolean.TRUE.equals(phase.getEtatFacturation()) ? "facturée" : "non facturée"
        );

        dto.setEtatPaiementLabel(
                Boolean.TRUE.equals(phase.getEtatPaiement()) ? "payée" : "non payée"
        );

        return dto;
    }
}