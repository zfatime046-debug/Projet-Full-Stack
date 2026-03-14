package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@Table(name = "projets")
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;
    private String nom;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double montant;

    @ManyToOne
    @JoinColumn(name = "organisme_id")
    private Organisme organisme;

    @ManyToOne
    @JoinColumn(name = "chef_projet_id")
    private Employe chefDeProjet;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Phase> phases;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    private List<Document> documents;
}