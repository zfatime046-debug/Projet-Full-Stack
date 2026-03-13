package com.example.demo.services;

import com.example.demo.dto.AffectationRequest;
import com.example.demo.entities.Affectation;

import java.util.List;

public interface AffectationService {

    Affectation createAffectation(AffectationRequest request);

    Affectation updateAffectation(AffectationRequest request);

    Affectation getAffectation(Long employeId, Long phaseId);

    List<Affectation> getAllAffectations();

    List<Affectation> getAffectationsByPhase(Long phaseId);

    List<Affectation> getAffectationsByEmploye(Long employeId);

    void deleteAffectation(Long employeId, Long phaseId);
}