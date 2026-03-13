package com.example.demo.dto;

import lombok.Data;

@Data
public class LivrableResponseDTO {
    private Long id;
    private String code;
    private String libelle;
    private String description;
    private String chemin;
    private Long phaseId;
}