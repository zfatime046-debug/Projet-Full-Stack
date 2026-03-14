package com.example.demo.services;

import com.example.demo.dto.PhaseRequest;
import com.example.demo.entities.Phase;

import java.util.List;

public interface PhaseService {

    Phase createPhase(PhaseRequest request);
    Phase updateEtatRealisation(Long id, Boolean etat);
    Phase updateEtatFacturation(Long id, Boolean etat);
    Phase updateEtatPaiement(Long id, Boolean etat);

    Phase updatePhase(Long id, PhaseRequest request);

    Phase getPhaseById(Long id);

    List<Phase> getAllPhases();

    List<Phase> getPhasesByProjet(Long projetId);

    void deletePhase(Long id);
}