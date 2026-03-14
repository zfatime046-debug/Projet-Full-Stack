package com.example.demo.services;

import com.example.demo.dto.EmployeRequestDTO;
import com.example.demo.dto.EmployeResponseDTO;
import com.example.demo.entities.Employe;
import com.example.demo.mappers.EmployeMapper;
import com.example.demo.repositories.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final EmployeMapper employeMapper;

    public EmployeResponseDTO creer(EmployeRequestDTO dto) {
        // Contrôle unicité
        if (employeRepository.findByMatricule(dto.getMatricule()).isPresent())
            throw new RuntimeException("Matricule déjà utilisé");
        if (employeRepository.findByLogin(dto.getLogin()).isPresent())
            throw new RuntimeException("Login déjà utilisé");
        if (employeRepository.findByEmail(dto.getEmail()).isPresent())
            throw new RuntimeException("Email déjà utilisé");

        Employe e = new Employe();
        e.setMatricule(dto.getMatricule());
        e.setNom(dto.getNom());
        e.setPrenom(dto.getPrenom());
        e.setTelephone(dto.getTelephone());
        e.setEmail(dto.getEmail());
        e.setLogin(dto.getLogin());
        e.setPassword(dto.getPassword()); // encoder en phase sécurité
        // e.setProfil(...) → à compléter avec ProfilRepository si besoin

        return employeMapper.toDTO(employeRepository.save(e));
    }

    public EmployeResponseDTO modifier(Long id, EmployeRequestDTO dto) {
        Employe e = employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));
        e.setNom(dto.getNom());
        e.setPrenom(dto.getPrenom());
        e.setTelephone(dto.getTelephone());
        e.setEmail(dto.getEmail());
        return employeMapper.toDTO(employeRepository.save(e));
    }

    public EmployeResponseDTO getById(Long id) {
        return employeMapper.toDTO(
                employeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Employé introuvable"))
        );
    }

    public List<EmployeResponseDTO> getAll() {
        return employeRepository.findAll()
                .stream().map(employeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void supprimer(Long id) {
        employeRepository.deleteById(id);
    }

    public List<Employe> getEmployesDisponibles(LocalDate dateDebut, LocalDate dateFin) {
        return employeRepository.findEmployesDisponibles(dateDebut, dateFin);
    }
}