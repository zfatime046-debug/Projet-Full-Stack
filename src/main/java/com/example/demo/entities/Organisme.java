package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "organismes")
public class Organisme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String nom;
    private String adresse;
    private String telephone;
    private String nomContact;
    private String emailContact;
    private String siteWeb;
}