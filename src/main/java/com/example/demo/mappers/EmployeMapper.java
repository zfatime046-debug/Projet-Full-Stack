package com.example.demo.mappers;

import com.example.demo.dto.EmployeResponseDTO;
import com.example.demo.entities.Employe;
import org.springframework.stereotype.Component;

@Component
public class EmployeMapper {

    public EmployeResponseDTO toDTO(Employe e) {
        EmployeResponseDTO dto = new EmployeResponseDTO();
        dto.setId(e.getId());
        dto.setMatricule(e.getMatricule());
        dto.setNom(e.getNom());
        dto.setPrenom(e.getPrenom());
        dto.setTelephone(e.getTelephone());
        dto.setEmail(e.getEmail());
        dto.setLogin(e.getLogin());
        if (e.getProfil() != null) {
            dto.setProfilNom(e.getProfil().getLibelle());
        }
        return dto;
    }
}