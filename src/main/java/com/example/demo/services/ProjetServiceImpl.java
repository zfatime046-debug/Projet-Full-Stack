package com.example.demo.services;

import com.example.demo.dto.ProjetRequest;
import com.example.demo.entities.Employe;
import com.example.demo.entities.Organisme;
import com.example.demo.entities.Projet;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.mappers.ProjetMapper;
import com.example.demo.repositories.EmployeRepository;
import com.example.demo.repositories.OrganismeRepository;
import com.example.demo.repositories.ProjetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetServiceImpl implements ProjetService {

    private final ProjetRepository projetRepository;
    private final OrganismeRepository organismeRepository;
    private final EmployeRepository employeRepository;

    public ProjetServiceImpl(ProjetRepository projetRepository,
                             OrganismeRepository organismeRepository,
                             EmployeRepository employeRepository) {
        this.projetRepository = projetRepository;
        this.organismeRepository = organismeRepository;
        this.employeRepository = employeRepository;
    }

    @Override
    public Projet createProjet(ProjetRequest request) {
        validateProjet(request, null);

        Organisme organisme = organismeRepository.findById(request.getOrganismeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organisme introuvable avec id : " + request.getOrganismeId()
                ));

        Employe chefDeProjet = null;
        if (request.getChefDeProjetId() != null) {
            chefDeProjet = employeRepository.findById(request.getChefDeProjetId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Employé introuvable avec id : " + request.getChefDeProjetId()
                    ));
        }

        Projet projet = ProjetMapper.toEntity(request);
        projet.setOrganisme(organisme);
        projet.setChefDeProjet(chefDeProjet);

        return projetRepository.save(projet);
    }

    @Override
    public Projet updateProjet(Long id, ProjetRequest request) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet introuvable avec id : " + id));

        validateProjet(request, id);

        Organisme organisme = organismeRepository.findById(request.getOrganismeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organisme introuvable avec id : " + request.getOrganismeId()
                ));

        Employe chefDeProjet = null;
        if (request.getChefDeProjetId() != null) {
            chefDeProjet = employeRepository.findById(request.getChefDeProjetId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Employé introuvable avec id : " + request.getChefDeProjetId()
                    ));
        }

        projet.setCode(request.getCode());
        projet.setNom(request.getNom());
        projet.setDescription(request.getDescription());
        projet.setDateDebut(request.getDateDebut());
        projet.setDateFin(request.getDateFin());
        projet.setMontant(request.getMontant());
        projet.setOrganisme(organisme);
        projet.setChefDeProjet(chefDeProjet);

        return projetRepository.save(projet);
    }

    @Override
    public Projet getProjetById(Long id) {
        return projetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet introuvable avec id : " + id));
    }

    @Override
    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }

    @Override
    public List<Projet> getProjetsByOrganisme(Long organismeId) {
        return projetRepository.findByOrganismeId(organismeId);
    }

    @Override
    public List<Projet> getProjetsByChefDeProjet(Long employeId) {
        return projetRepository.findByChefDeProjetId(employeId);
    }

    @Override
    public void deleteProjet(Long id) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet introuvable avec id : " + id));

        projetRepository.delete(projet);
    }

    private void validateProjet(ProjetRequest request, Long projetId) {
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            throw new BadRequestException("Le code du projet est obligatoire");
        }

        if (request.getNom() == null || request.getNom().trim().isEmpty()) {
            throw new BadRequestException("Le nom du projet est obligatoire");
        }

        if (request.getDateDebut() == null || request.getDateFin() == null) {
            throw new BadRequestException("Les dates du projet sont obligatoires");
        }

        if (request.getDateDebut().isAfter(request.getDateFin())) {
            throw new BadRequestException("La date début doit être avant ou égale à la date fin");
        }

        if (request.getMontant() < 0) {
            throw new BadRequestException("Le montant doit être positif");
        }

        if (request.getOrganismeId() == null) {
            throw new BadRequestException("L'organisme est obligatoire");
        }

        Optional<Projet> existingProjet = projetRepository.findByCode(request.getCode());

        if (projetId == null) {
            if (existingProjet.isPresent()) {
                throw new BadRequestException("Un projet avec ce code existe déjà");
            }
        } else {
            if (existingProjet.isPresent() && !existingProjet.get().getId().equals(projetId)) {
                throw new BadRequestException("Un autre projet avec ce code existe déjà");
            }
        }
    }
}