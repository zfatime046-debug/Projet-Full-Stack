package com.example.demo.repositories;

import com.example.demo.entities.Affectation;
import com.example.demo.entities.AffectationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AffectationRepository extends JpaRepository<Affectation, AffectationId> {

    List<Affectation> findByPhase_Id(Long phaseId);

    List<Affectation> findByEmploye_Id(Long employeId);
}