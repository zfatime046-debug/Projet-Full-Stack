package com.example.demo.mappers;

import com.example.demo.dto.LivrableRequestDTO;
import com.example.demo.dto.LivrableResponseDTO;
import com.example.demo.entities.Livrable;
import org.springframework.stereotype.Component;

@Component
public class LivrableMapper {

    public Livrable toEntity(LivrableRequestDTO dto) {
        Livrable l = new Livrable();
        l.setCode(dto.getCode());
        l.setLibelle(dto.getLibelle());
        l.setDescription(dto.getDescription());
        l.setChemin(dto.getChemin());
        return l;
    }

    public LivrableResponseDTO toDTO(Livrable l) {
        LivrableResponseDTO dto = new LivrableResponseDTO();
        dto.setId(l.getId());
        dto.setCode(l.getCode());
        dto.setLibelle(l.getLibelle());
        dto.setDescription(l.getDescription());
        dto.setChemin(l.getChemin());
        if (l.getPhase() != null) {
            dto.setPhaseId(l.getPhase().getId());
        }
        return dto;
    }
}