package com.example.demo.repositories;

import com.example.demo.entities.Phase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhaseRepository extends JpaRepository<Phase, Long> {

    Optional<Phase> findByCode(String code);

    boolean existsByCode(String code);

    List<Phase> findByProjetId(Long projetId);


    // Requêtes dérivées simples

    List<Phase> findByEtatRealisationAndEtatFacturation(String etatRealisation, String etatFacturation);

    List<Phase> findByEtatFacturationAndEtatPaiement(String etatFacturation, String etatPaiement);

    List<Phase> findByEtatPaiement(String etatPaiement);

    List<Phase> findByProjetIdAndEtatRealisationAndEtatFacturation(
            Long projetId,
            String etatRealisation,
            String etatFacturation
    );

    List<Phase> findByProjetIdAndEtatFacturationAndEtatPaiement(
            Long projetId,
            String etatFacturation,
            String etatPaiement
    );

    List<Phase> findByProjetIdAndEtatPaiement(
            Long projetId,
            String etatPaiement
    );

    // Pagination

    Page<Phase> findByEtatRealisationAndEtatFacturation(
            String etatRealisation,
            String etatFacturation,
            Pageable pageable
    );

    Page<Phase> findByEtatFacturationAndEtatPaiement(
            String etatFacturation,
            String etatPaiement,
            Pageable pageable
    );

    Page<Phase> findByEtatPaiement(
            String etatPaiement,
            Pageable pageable
    );

    // Requêtes personnalisées

    @Query("SELECT p FROM Phase p WHERE p.etatRealisation = :etatRealisation AND p.etatFacturation = :etatFacturation")
    List<Phase> findPhasesByEtatRealisationAndFacturation(
            @Param("etatRealisation") String etatRealisation,
            @Param("etatFacturation") String etatFacturation
    );

    @Query("SELECT p FROM Phase p WHERE p.etatFacturation = :etatFacturation AND p.etatPaiement = :etatPaiement")
    List<Phase> findPhasesByEtatFacturationAndPaiement(
            @Param("etatFacturation") String etatFacturation,
            @Param("etatPaiement") String etatPaiement
    );

    @Query("SELECT p FROM Phase p WHERE p.etatPaiement = :etatPaiement")
    List<Phase> findPhasesByEtatPaiementCustom(
            @Param("etatPaiement") String etatPaiement
    );

    @Query("SELECT p FROM Phase p WHERE p.projet.id = :projetId AND p.etatRealisation = :etatRealisation AND p.etatFacturation = :etatFacturation")
    List<Phase> findPhasesTermineesNonFactureesByProjetCustom(
            @Param("projetId") Long projetId,
            @Param("etatRealisation") String etatRealisation,
            @Param("etatFacturation") String etatFacturation
    );

    @Query("SELECT p FROM Phase p WHERE p.projet.id = :projetId AND p.etatFacturation = :etatFacturation AND p.etatPaiement = :etatPaiement")
    List<Phase> findPhasesFactureesNonPayeesByProjetCustom(
            @Param("projetId") Long projetId,
            @Param("etatFacturation") String etatFacturation,
            @Param("etatPaiement") String etatPaiement
    );

    @Query("SELECT p FROM Phase p WHERE p.projet.id = :projetId AND p.etatPaiement = :etatPaiement")
    List<Phase> findPhasesPayeesByProjetCustom(
            @Param("projetId") Long projetId,
            @Param("etatPaiement") String etatPaiement
    );

    @Query("SELECT p FROM Phase p WHERE p.etatPaiement = :etatPaiement")
    Page<Phase> findPhasesPayeesCustomPage(
            @Param("etatPaiement") String etatPaiement,
            Pageable pageable
    );
}