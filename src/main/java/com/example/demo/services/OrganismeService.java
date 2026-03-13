package com.example.demo.services;

import com.example.demo.dto.OrganismeRequestDTO;
import com.example.demo.dto.OrganismeResponseDTO;
import com.example.demo.entities.Organisme;
import com.example.demo.mappers.OrganismeMapper;
import com.example.demo.repositories.OrganismeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganismeService {

    private final OrganismeRepository organismeRepository;
    private final OrganismeMapper organismeMapper;

    public OrganismeResponseDTO creer(OrganismeRequestDTO dto) {
        Organisme organisme = organismeMapper.toEntity(dto);
        return organismeMapper.toDTO(organismeRepository.save(organisme));
    }

    public OrganismeResponseDTO modifier(Long id, OrganismeRequestDTO dto) {
        Organisme organisme = organismeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organisme non trouvé"));
        organisme.setCode(dto.getCode());
        organisme.setNom(dto.getNom());
        organisme.setAdresse(dto.getAdresse());
        organisme.setTelephone(dto.getTelephone());
        organisme.setNomContact(dto.getNomContact());
        organisme.setEmailContact(dto.getEmailContact());
        organisme.setSiteWeb(dto.getSiteWeb());
        return organismeMapper.toDTO(organismeRepository.save(organisme));
    }

    public OrganismeResponseDTO getById(Long id) {
        Organisme organisme = organismeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organisme non trouvé"));
        return organismeMapper.toDTO(organisme);
    }

    public List<OrganismeResponseDTO> getAll() {
        return organismeRepository.findAll()
                .stream()
                .map(organismeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void supprimer(Long id) {
        organismeRepository.deleteById(id);
    }

    public List<OrganismeResponseDTO> rechercherParNom(String nom) {
        return organismeRepository.findByNomContaining(nom)
                .stream().map(organismeMapper::toDTO).collect(Collectors.toList());
    }

    public OrganismeResponseDTO rechercherParCode(String code) {
        Organisme organisme = organismeRepository.findByCode(code);
        if (organisme == null) throw new RuntimeException("Organisme non trouvé");
        return organismeMapper.toDTO(organisme);
    }

    public List<OrganismeResponseDTO> rechercherParContact(String contact) {
        return organismeRepository.findByNomContactContaining(contact)
                .stream().map(organismeMapper::toDTO).collect(Collectors.toList());
    }
}