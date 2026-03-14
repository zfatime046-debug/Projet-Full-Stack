package com.example.demo.mappers;

import com.example.demo.dto.DocumentDTO;
import com.example.demo.entities.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    
    public Document toEntity(DocumentDTO dto) {
        Document document = new Document();
        document.setId(dto.getId());
        document.setCode(dto.getCode());
        document.setLibelle(dto.getLibelle());
        document.setDescription(dto.getDescription());
        document.setChemin(dto.getChemin());
        // La relation avec Projet est gérée dans le service
        return document;
    }

    
    public DocumentDTO toDTO(Document document) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setCode(document.getCode());
        dto.setLibelle(document.getLibelle());
        dto.setDescription(document.getDescription());
        dto.setChemin(document.getChemin());
        if (document.getProjet() != null) {
            dto.setProjetId(document.getProjet().getId());
        }
        return dto;
    }
}
