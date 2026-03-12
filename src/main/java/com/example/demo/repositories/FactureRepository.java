package com.example.demo.repositories;

import com.example.demo.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    Optional<Facture> findByPhaseId(Long phaseId);
    Optional<Facture> findByCode(String code);
}