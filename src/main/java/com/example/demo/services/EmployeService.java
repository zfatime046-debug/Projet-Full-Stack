package com.example.demo.services;

import com.example.demo.dto.EmployeRequestDTO;
import com.example.demo.dto.EmployeResponseDTO;
import com.example.demo.entities.Employe;
import com.example.demo.entities.Profil;
import com.example.demo.mappers.EmployeMapper;
import com.example.demo.repositories.EmployeRepository;
import com.example.demo.repositories.ProfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final ProfilRepository profilRepository;
    private final EmployeMapper employeMapper;
    private final PasswordEncoder passwordEncoder;

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
        e.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Récupérer le profil si fourni
        if (dto.getProfilId() != null) {
            Profil profil = profilRepository.findById(dto.getProfilId())
                    .orElseThrow(() -> new RuntimeException("Profil non trouvé"));
            e.setProfil(profil);
        }

        return employeMapper.toDTO(employeRepository.save(e));
    }

    public EmployeResponseDTO modifier(Long id, EmployeRequestDTO dto) {
        Employe e = employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

        // Vérifier unicité si login changé
        if (!e.getLogin().equals(dto.getLogin()) &&
                employeRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new RuntimeException("Login déjà utilisé");
        }
        // Vérifier unicité si email changé
        if (!e.getEmail().equals(dto.getEmail()) &&
                employeRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        e.setNom(dto.getNom());
        e.setPrenom(dto.getPrenom());
        e.setTelephone(dto.getTelephone());
        e.setEmail(dto.getEmail());
        e.setLogin(dto.getLogin());

        // Mettre à jour le mot de passe seulement s'il est fourni
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            e.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // Mettre à jour le profil si fourni
        if (dto.getProfilId() != null) {
            Profil profil = profilRepository.findById(dto.getProfilId())
                    .orElseThrow(() -> new RuntimeException("Profil non trouvé"));
            e.setProfil(profil);
        }

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
    public void changePassword(Long employeId, String newPassword) {
        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));
        employe.setPassword(passwordEncoder.encode(newPassword));
        employeRepository.save(employe);
    }
}