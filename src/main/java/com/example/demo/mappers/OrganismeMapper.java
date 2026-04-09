package com.example.demo.mappers;

import com.example.demo.dto.OrganismeRequestDTO;
import com.example.demo.dto.OrganismeResponseDTO;
import com.example.demo.entities.Organisme;
import org.springframework.stereotype.Component;

@Component
public class OrganismeMapper {

    public Organisme toEntity(OrganismeRequestDTO dto) {
        Organisme o = new Organisme();
        o.setCode(dto.getCode());
        o.setNom(dto.getNom());
        o.setAdresse(dto.getAdresse());
        o.setTelephone(dto.getTelephone());
        o.setNomContact(dto.getNomContact());
        o.setEmailContact(dto.getEmailContact());
        o.setSiteWeb(dto.getSiteWeb());
        return o;
    }

    public OrganismeResponseDTO toDTO(Organisme o) {
        OrganismeResponseDTO dto = new OrganismeResponseDTO();
        dto.setId(o.getId());
        dto.setCode(o.getCode());
        dto.setNom(o.getNom());
        dto.setAdresse(o.getAdresse());
        dto.setTelephone(o.getTelephone());
        dto.setNomContact(o.getNomContact());
        dto.setEmailContact(o.getEmailContact());
        dto.setSiteWeb(o.getSiteWeb());
        return dto;
    }
}