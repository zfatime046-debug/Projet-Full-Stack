package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "affectations")
public class Affectation {

    @EmbeddedId
    private AffectationId id;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ManyToOne
    @MapsId("employeId")
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne
    @MapsId("phaseId")
    @JoinColumn(name = "phase_id")
    private Phase phase;
}