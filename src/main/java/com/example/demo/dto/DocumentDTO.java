package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DocumentDTO {

    private Long id;

    @NotBlank(message = "Le code est obligatoire")
    private String code;

    @NotBlank(message = "Le libellé est obligatoire")
    private String libelle;

    private String description;

    @NotBlank(message = "Le chemin est obligatoire")
    private String chemin;

    @NotNull(message = "Le projet est obligatoire")
    private Long projetId;
}