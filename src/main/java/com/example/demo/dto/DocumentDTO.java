package com.example.demo.dto;

import lombok.Data;

@Data
public class DocumentDTO {
    private Long id;
    private String code;
    private String libelle;
    private String description;
    private String chemin;
    private Long projetId;
}