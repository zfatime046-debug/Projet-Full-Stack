package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "factures")
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private LocalDate dateFacture;
    private BigDecimal montant;

    @Enumerated(EnumType.STRING)
    private StatutFacture statut; // EMISE, PAYEE, ANNULEE

    @OneToOne
    @JoinColumn(name = "phase_id", unique = true)
    private Phase phase;
}