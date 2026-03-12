package com.example.demo.repositories;

import com.example.demo.entities.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
    List<Phase> findByProjetId(Long projetId);
    List<Phase> findByEtatRealisationTrueAndEtatFacturationFalse();
    List<Phase> findByEtatFacturationTrueAndEtatPaiementFalse();
}