package com.example.demo.repositories;

import com.example.demo.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProjetRepository extends JpaRepository<Projet, Long> {
    Optional<Projet> findByCode(String code);
    List<Projet> findByOrganismeId(Long organismeId);
    List<Projet> findByChefDeProjetId(Long employeId);
}