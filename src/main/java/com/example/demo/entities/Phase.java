package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;



@Entity
@Data
@Table(name = "phases")
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String libelle;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double montant;
    private String etatRealisation;
    private String etatFacturation;
    private String etatPaiement;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    @JsonBackReference
    private Projet projet;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    private List<Livrable> livrables;

    @OneToOne(mappedBy = "phase", cascade = CascadeType.ALL)
    private Facture facture;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Affectation> affectations;
}