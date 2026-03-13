package com.example.demo.dto;

import lombok.Data;

@Data
public class LivrableRequestDTO {
    private String code;
    private String libelle;
    private String description;
    private String chemin;
}