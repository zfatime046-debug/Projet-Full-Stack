package com.example.demo.repositories;

import com.example.demo.entities.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
    Optional<Phase> findByCode(String code);
    boolean existsByCode(String code);
    List<Phase> findByProjetId(Long projetId);
    List<Phase> findByEtatRealisationTrueAndEtatFacturationFalse();
    List<Phase> findByEtatFacturationTrueAndEtatPaiementFalse();
}