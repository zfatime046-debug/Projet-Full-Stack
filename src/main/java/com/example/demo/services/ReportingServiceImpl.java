package com.example.demo.services;

import com.example.demo.dto.TableauDeBordDTO;
import com.example.demo.entities.Phase;
import com.example.demo.entities.Projet;
import com.example.demo.repositories.PhaseRepository;
import com.example.demo.repositories.ProjetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportingServiceImpl implements ReportingService {

    private final PhaseRepository phaseRepository;
    private final ProjetRepository projetRepository;

    @Override
    public List<Phase> getPhasesTermineesNonFacturees() {
        // "realise" → true, "non facture" → false
        return phaseRepository.findByEtatRealisationAndEtatFacturation(true, false);
    }

    @Override
    public List<Phase> getPhasesFactureesNonPayees() {
        // "facture" → true, "non paye" → false
        return phaseRepository.findByEtatFacturationAndEtatPaiement(true, false);
    }

    @Override
    public List<Phase> getPhasesPayees() {
        // "paye" → true
        return phaseRepository.findByEtatPaiement(true);
    }

    @Override
    public List<Projet> getProjetsEnCours() {
        LocalDate today = LocalDate.now();
        return projetRepository.findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(today, today);
    }

    @Override
    public List<Projet> getProjetsClotures() {
        return projetRepository.findByDateFinBefore(LocalDate.now());
    }

    @Override
    public TableauDeBordDTO getTableauDeBord() {
        List<Projet> projetsEnCours = getProjetsEnCours();
        List<Projet> projetsClotures = getProjetsClotures();
        List<Phase> phasesTermineesNonFacturees = getPhasesTermineesNonFacturees();
        List<Phase> phasesFactureesNonPayees = getPhasesFactureesNonPayees();
        List<Phase> phasesPayees = getPhasesPayees();

        TableauDeBordDTO dto = new TableauDeBordDTO();
        dto.setNombreProjetsEnCours(projetsEnCours.size());
        dto.setNombreProjetsClotures(projetsClotures.size());
        dto.setNombrePhasesTermineesNonFacturees(phasesTermineesNonFacturees.size());
        dto.setNombrePhasesFactureesNonPayees(phasesFactureesNonPayees.size());
        dto.setNombrePhasesPayees(phasesPayees.size());

        return dto;
    }

    @Override
    public List<Projet> getProjetsEnCoursByChef(Long chefProjetId) {
        LocalDate today = LocalDate.now();
        return projetRepository.findByChefDeProjetIdAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
                chefProjetId, today, today
        );
    }

    @Override
    public List<Projet> getProjetsCloturesByChef(Long chefProjetId) {
        return projetRepository.findByChefDeProjetIdAndDateFinBefore(
                chefProjetId, LocalDate.now()
        );
    }

    @Override
    public List<Phase> getPhasesTermineesNonFactureesByProjet(Long projetId) {
        // "realise" → true, "non facture" → false
        return phaseRepository.findByProjetIdAndEtatRealisationAndEtatFacturation(
                projetId, true, false
        );
    }

    @Override
    public List<Phase> getPhasesFactureesNonPayeesByProjet(Long projetId) {
        // "facture" → true, "non paye" → false
        return phaseRepository.findByProjetIdAndEtatFacturationAndEtatPaiement(
                projetId, true, false
        );
    }

    @Override
    public List<Phase> getPhasesPayeesByProjet(Long projetId) {
        // "paye" → true
        return phaseRepository.findByProjetIdAndEtatPaiement(
                projetId, true
        );
    }

    @Override
    public List<Projet> getProjetsEnCoursByPeriode(LocalDate dateDebut, LocalDate dateFin) {
        return projetRepository.findByDateDebutBetween(dateDebut, dateFin);
    }

    @Override
    public List<Projet> getProjetsCloturesByPeriode(LocalDate dateDebut, LocalDate dateFin) {
        return projetRepository.findByDateFinBetween(dateDebut, dateFin);
    }

    @Override
    public Page<Phase> getPhasesTermineesNonFactureesPage(int page, int size) {
        // "realise" → true, "non facture" → false
        return phaseRepository.findByEtatRealisationAndEtatFacturation(
                true, false, PageRequest.of(page, size)
        );
    }

    @Override
    public Page<Phase> getPhasesFactureesNonPayeesPage(int page, int size) {
        // "facture" → true, "non paye" → false
        return phaseRepository.findByEtatFacturationAndEtatPaiement(
                true, false, PageRequest.of(page, size)
        );
    }

    @Override
    public Page<Phase> getPhasesPayeesPage(int page, int size) {
        // "paye" → true
        return phaseRepository.findByEtatPaiement(
                true, PageRequest.of(page, size)
        );
    }
}