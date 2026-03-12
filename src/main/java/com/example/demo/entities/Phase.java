package com.example.demo.entities;

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
    private boolean etatRealisation;
    private boolean etatFacturation;
    private boolean etatPaiement;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    private List<Livrable> livrables;

    @OneToOne(mappedBy = "phase", cascade = CascadeType.ALL)
    private Facture facture;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    private List<Affectation> affectations;
}