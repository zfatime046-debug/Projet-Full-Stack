package com.example.demo.repositories;

import com.example.demo.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjetRepository extends JpaRepository<Projet, Long> {
    Optional<Projet> findByCode(String code);
    List<Projet> findByOrganismeId(Long organismeId);
    List<Projet> findByChefDeProjetId(Long employeId);
    List<Projet> findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(LocalDate date1, LocalDate date2);

    List<Projet> findByDateFinBefore(LocalDate date);
    List<Projet> findByChefDeProjetIdAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(Long chefProjetId, LocalDate date1, LocalDate date2);

    List<Projet> findByChefDeProjetIdAndDateFinBefore(Long chefProjetId, LocalDate date);
    List<Projet> findByDateDebutBetween(LocalDate dateDebut, LocalDate dateFin);
    List<Projet> findByDateFinBetween(LocalDate dateDebut, LocalDate dateFin);
}