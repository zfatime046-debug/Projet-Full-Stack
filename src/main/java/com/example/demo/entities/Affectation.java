package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employe employe;

    @ManyToOne
    @MapsId("phaseId")
    @JoinColumn(name = "phase_id")
    @JsonBackReference
    private Phase phase;
}