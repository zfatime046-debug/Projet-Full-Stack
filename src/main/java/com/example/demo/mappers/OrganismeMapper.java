package com.example.demo.mappers;

import com.example.demo.dto.OrganismeRequestDTO;
import com.example.demo.dto.OrganismeResponseDTO;
import com.example.demo.entities.Organisme;
import org.springframework.stereotype.Component;

@Component
public class OrganismeMapper {

    public Organisme toEntity(OrganismeRequestDTO dto) {
        Organisme organisme = new Organisme();
        organisme.setCode(dto.getCode());
        organisme.setNom(dto.getNom());
        organisme.setAdresse(dto.getAdresse());
        organisme.setTelephone(dto.getTelephone());
        organisme.setNomContact(dto.getNomContact());
        organisme.setEmailContact(dto.getEmailContact());
        organisme.setSiteWeb(dto.getSiteWeb());
        return organisme;
    }

    public OrganismeResponseDTO toDTO(Organisme organisme) {
        OrganismeResponseDTO dto = new OrganismeResponseDTO();
        dto.setId(organisme.getId());
        dto.setCode(organisme.getCode());
        dto.setNom(organisme.getNom());
        dto.setAdresse(organisme.getAdresse());
        dto.setTelephone(organisme.getTelephone());
        dto.setNomContact(organisme.getNomContact());
        dto.setEmailContact(organisme.getEmailContact());
        dto.setSiteWeb(organisme.getSiteWeb());
        return dto;
    }
}