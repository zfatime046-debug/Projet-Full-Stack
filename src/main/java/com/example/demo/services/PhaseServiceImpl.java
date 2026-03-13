package com.example.demo.services;

import com.example.demo.dto.PhaseRequest;
import com.example.demo.entities.Phase;
import com.example.demo.entities.Projet;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.mappers.PhaseMapper;
import com.example.demo.repositories.PhaseRepository;
import com.example.demo.repositories.ProjetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhaseServiceImpl implements PhaseService {

    private final PhaseRepository phaseRepository;
    private final ProjetRepository projetRepository;

    public PhaseServiceImpl(PhaseRepository phaseRepository, ProjetRepository projetRepository) {
        this.phaseRepository = phaseRepository;
        this.projetRepository = projetRepository;
    }

    @Override
    public Phase createPhase(PhaseRequest request) {
        validatePhase(request, null);

        Projet projet = projetRepository.findById(request.getProjetId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Projet introuvable avec id : " + request.getProjetId()
                ));

        validateDatesInProjet(request, projet);
        validateMontantPhases(request, projet, null);

        Phase phase = PhaseMapper.toEntity(request);
        phase.setProjet(projet);

        return phaseRepository.save(phase);
    }

    @Override
    public Phase updatePhase(Long id, PhaseRequest request) {
        Phase phase = phaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phase introuvable avec id : " + id));

        validatePhase(request, id);

        Projet projet = projetRepository.findById(request.getProjetId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Projet introuvable avec id : " + request.getProjetId()
                ));

        validateDatesInProjet(request, projet);
        validateMontantPhases(request, projet, id);

        phase.setCode(request.getCode());
        phase.setLibelle(request.getLibelle());
        phase.setDescription(request.getDescription());
        phase.setDateDebut(request.getDateDebut());
        phase.setDateFin(request.getDateFin());
        phase.setMontant(request.getMontant());
        phase.setEtatRealisation(request.isEtatRealisation());
        phase.setEtatFacturation(request.isEtatFacturation());
        phase.setEtatPaiement(request.isEtatPaiement());
        phase.setProjet(projet);

        return phaseRepository.save(phase);
    }

    @Override
    public Phase getPhaseById(Long id) {
        return phaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phase introuvable avec id : " + id));
    }

    @Override
    public List<Phase> getAllPhases() {
        return phaseRepository.findAll();
    }

    @Override
    public List<Phase> getPhasesByProjet(Long projetId) {
        return phaseRepository.findByProjetId(projetId);
    }

    @Override
    public void deletePhase(Long id) {
        Phase phase = phaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phase introuvable avec id : " + id));

        phaseRepository.delete(phase);
    }

    private void validatePhase(PhaseRequest request, Long phaseId) {
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            throw new BadRequestException("Le code de la phase est obligatoire");
        }

        if (request.getLibelle() == null || request.getLibelle().trim().isEmpty()) {
            throw new BadRequestException("Le libellé de la phase est obligatoire");
        }

        if (request.getDateDebut() == null || request.getDateFin() == null) {
            throw new BadRequestException("Les dates de la phase sont obligatoires");
        }

        if (request.getDateDebut().isAfter(request.getDateFin())) {
            throw new BadRequestException("La date début de la phase doit être avant ou égale à la date fin");
        }

        if (request.getMontant() < 0) {
            throw new BadRequestException("Le montant de la phase doit être positif");
        }

        if (request.getProjetId() == null) {
            throw new BadRequestException("Le projet est obligatoire");
        }

        Optional<Phase> existingPhase = phaseRepository.findByCode(request.getCode());

        if (phaseId == null) {
            if (existingPhase.isPresent()) {
                throw new BadRequestException("Une phase avec ce code existe déjà");
            }
        } else {
            if (existingPhase.isPresent() && !existingPhase.get().getId().equals(phaseId)) {
                throw new BadRequestException("Une autre phase avec ce code existe déjà");
            }
        }
    }

    private void validateDatesInProjet(PhaseRequest request, Projet projet) {
        if (request.getDateDebut().isBefore(projet.getDateDebut()) ||
                request.getDateFin().isAfter(projet.getDateFin())) {
            throw new BadRequestException("Les dates de la phase doivent être incluses dans les dates du projet");
        }
    }

    private void validateMontantPhases(PhaseRequest request, Projet projet, Long phaseIdToExclude) {
        double somme = 0;

        if (projet.getPhases() != null) {
            somme = projet.getPhases().stream()
                    .filter(p -> phaseIdToExclude == null || !p.getId().equals(phaseIdToExclude))
                    .mapToDouble(Phase::getMontant)
                    .sum();
        }

        somme += request.getMontant();

        if (somme > projet.getMontant()) {
            throw new BadRequestException("La somme des montants des phases dépasse le montant du projet");
        }
    }
}
