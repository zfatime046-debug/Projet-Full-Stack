package com.example.demo.repositories;

import com.example.demo.entities.Livrable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivrableRepository extends JpaRepository<Livrable, Long> {
    List<Livrable> findByPhaseId(Long phaseId);
}