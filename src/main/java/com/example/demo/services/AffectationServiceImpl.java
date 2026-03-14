package com.example.demo.services;

import com.example.demo.dto.AffectationRequest;
import com.example.demo.entities.Affectation;
import com.example.demo.entities.AffectationId;
import com.example.demo.entities.Employe;
import com.example.demo.entities.Phase;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.mappers.AffectationMapper;
import com.example.demo.repositories.AffectationRepository;
import com.example.demo.repositories.EmployeRepository;
import com.example.demo.repositories.PhaseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AffectationServiceImpl implements AffectationService {

    private final AffectationRepository affectationRepository;
    private final EmployeRepository employeRepository;
    private final PhaseRepository phaseRepository;

    public AffectationServiceImpl(AffectationRepository affectationRepository,
                                  EmployeRepository employeRepository,
                                  PhaseRepository phaseRepository) {
        this.affectationRepository = affectationRepository;
        this.employeRepository = employeRepository;
        this.phaseRepository = phaseRepository;
    }

    @Override
    public Affectation createAffectation(AffectationRequest request) {
        validateAffectation(request);

        Employe employe = employeRepository.findById(request.getEmployeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employé introuvable avec id : " + request.getEmployeId()
                ));

        Phase phase = phaseRepository.findById(request.getPhaseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Phase introuvable avec id : " + request.getPhaseId()
                ));

        validateDatesInPhase(request, phase);

        AffectationId id = new AffectationId();
        id.setEmployeId(request.getEmployeId());
        id.setPhaseId(request.getPhaseId());

        if (affectationRepository.existsById(id)) {
            throw new BadRequestException("Cette affectation existe déjà");
        }

        Affectation affectation = AffectationMapper.toEntity(request);
        affectation.setId(id);
        affectation.setEmploye(employe);
        affectation.setPhase(phase);

        return affectationRepository.save(affectation);
    }

    @Override
    public Affectation updateAffectation(AffectationRequest request) {
        validateAffectation(request);

        AffectationId id = new AffectationId();
        id.setEmployeId(request.getEmployeId());
        id.setPhaseId(request.getPhaseId());

        Affectation affectation = affectationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affectation introuvable"));

        Phase phase = phaseRepository.findById(request.getPhaseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Phase introuvable avec id : " + request.getPhaseId()
                ));

        validateDatesInPhase(request, phase);

        affectation.setDateDebut(request.getDateDebut());
        affectation.setDateFin(request.getDateFin());

        return affectationRepository.save(affectation);
    }

    @Override
    public Affectation getAffectation(Long employeId, Long phaseId) {
        AffectationId id = new AffectationId();
        id.setEmployeId(employeId);
        id.setPhaseId(phaseId);

        return affectationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affectation introuvable"));
    }

    @Override
    public List<Affectation> getAllAffectations() {
        return affectationRepository.findAll();
    }

    @Override
    public List<Affectation> getAffectationsByPhase(Long phaseId) {
        return affectationRepository.findByPhase_Id(phaseId);
    }

    @Override
    public List<Affectation> getAffectationsByEmploye(Long employeId) {
        return affectationRepository.findByEmploye_Id(employeId);
    }

    @Override
    public List<Phase> getPhasesByEmploye(Long employeId) {
        List<Affectation> affectations = affectationRepository.findByEmploye_Id(employeId);

        List<Phase> phases = new ArrayList<>();

        for (Affectation affectation : affectations) {
            phases.add(affectation.getPhase());
        }

        return phases;
    }

    @Override
    public void deleteAffectation(Long employeId, Long phaseId) {
        AffectationId id = new AffectationId();
        id.setEmployeId(employeId);
        id.setPhaseId(phaseId);

        Affectation affectation = affectationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affectation introuvable"));

        affectationRepository.delete(affectation);
    }

    private void validateAffectation(AffectationRequest request) {
        if (request.getEmployeId() == null) {
            throw new BadRequestException("L'employé est obligatoire");
        }

        if (request.getPhaseId() == null) {
            throw new BadRequestException("La phase est obligatoire");
        }

        if (request.getDateDebut() == null || request.getDateFin() == null) {
            throw new BadRequestException("Les dates de l'affectation sont obligatoires");
        }

        if (request.getDateDebut().isAfter(request.getDateFin())) {
            throw new BadRequestException("La date début de l'affectation doit être avant ou égale à la date fin");
        }
    }

    private void validateDatesInPhase(AffectationRequest request, Phase phase) {
        if (request.getDateDebut().isBefore(phase.getDateDebut()) ||
                request.getDateFin().isAfter(phase.getDateFin())) {
            throw new BadRequestException("Les dates de l'affectation doivent être incluses dans les dates de la phase");
        }
    }
}