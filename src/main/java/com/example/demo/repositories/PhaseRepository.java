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


    List<Phase> findByEtatRealisationAndEtatFacturation(Boolean etatRealisation, Boolean etatFacturation);


    List<Phase> findByEtatFacturationAndEtatPaiement(Boolean etatFacturation, Boolean etatPaiement);


    List<Phase> findByEtatPaiement(Boolean etatPaiement);


    List<Phase> findByProjetIdAndEtatRealisationAndEtatFacturation(
            Long projetId,
            Boolean etatRealisation,
            Boolean etatFacturation
    );


    List<Phase> findByProjetIdAndEtatFacturationAndEtatPaiement(
            Long projetId,
            Boolean etatFacturation,
            Boolean etatPaiement
    );


    List<Phase> findByProjetIdAndEtatPaiement(
            Long projetId,
            Boolean etatPaiement
    );


    Page<Phase> findByEtatRealisationAndEtatFacturation(
            Boolean etatRealisation,
            Boolean etatFacturation,
            Pageable pageable
    );


    Page<Phase> findByEtatFacturationAndEtatPaiement(
            Boolean etatFacturation,
            Boolean etatPaiement,
            Pageable pageable
    );


    Page<Phase> findByEtatPaiement(
            Boolean etatPaiement,
            Pageable pageable
    );


    @Query("SELECT p FROM Phase p WHERE p.etatRealisation = :etatRealisation AND p.etatFacturation = :etatFacturation")
    List<Phase> findPhasesByEtatRealisationAndFacturation(
            @Param("etatRealisation") Boolean etatRealisation,
            @Param("etatFacturation") Boolean etatFacturation
    );

    @Query("SELECT p FROM Phase p WHERE p.etatFacturation = :etatFacturation AND p.etatPaiement = :etatPaiement")
    List<Phase> findPhasesByEtatFacturationAndPaiement(
            @Param("etatFacturation") Boolean etatFacturation,
            @Param("etatPaiement") Boolean etatPaiement
    );

    @Query("SELECT p FROM Phase p WHERE p.etatPaiement = :etatPaiement")
    List<Phase> findPhasesByEtatPaiementCustom(
            @Param("etatPaiement") Boolean etatPaiement
    );

    @Query("SELECT p FROM Phase p WHERE p.projet.id = :projetId AND p.etatRealisation = :etatRealisation AND p.etatFacturation = :etatFacturation")
    List<Phase> findPhasesTermineesNonFactureesByProjetCustom(
            @Param("projetId") Long projetId,
            @Param("etatRealisation") Boolean etatRealisation,
            @Param("etatFacturation") Boolean etatFacturation
    );

    @Query("SELECT p FROM Phase p WHERE p.projet.id = :projetId AND p.etatFacturation = :etatFacturation AND p.etatPaiement = :etatPaiement")
    List<Phase> findPhasesFactureesNonPayeesByProjetCustom(
            @Param("projetId") Long projetId,
            @Param("etatFacturation") Boolean etatFacturation,
            @Param("etatPaiement") Boolean etatPaiement
    );

    @Query("SELECT p FROM Phase p WHERE p.projet.id = :projetId AND p.etatPaiement = :etatPaiement")
    List<Phase> findPhasesPayeesByProjetCustom(
            @Param("projetId") Long projetId,
            @Param("etatPaiement") Boolean etatPaiement
    );

    @Query("SELECT p FROM Phase p WHERE p.etatPaiement = :etatPaiement")
    Page<Phase> findPhasesPayeesCustomPage(
            @Param("etatPaiement") Boolean etatPaiement,
            Pageable pageable
    );
}