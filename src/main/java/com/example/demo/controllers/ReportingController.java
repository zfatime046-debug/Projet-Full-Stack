package com.example.demo.controllers;

import com.example.demo.dto.TableauDeBordDTO;
import com.example.demo.entities.Phase;
import com.example.demo.entities.Projet;
import com.example.demo.services.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reporting")
@RequiredArgsConstructor
@CrossOrigin("*")
public class  ReportingController {

    private final ReportingService reportingService;
    @GetMapping("/projets/en-cours/chef/{chefProjetId}")
    public List<Projet> getProjetsEnCoursByChef(@PathVariable Long chefProjetId) {
        return reportingService.getProjetsEnCoursByChef(chefProjetId);
    }
    @GetMapping("/phases/terminees-non-facturees/page")
    public Page<Phase> getPhasesTermineesNonFactureesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return reportingService.getPhasesTermineesNonFactureesPage(page, size);
    }

    @GetMapping("/phases/facturees-non-payees/page")
    public Page<Phase> getPhasesFactureesNonPayeesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return reportingService.getPhasesFactureesNonPayeesPage(page, size);
    }

    @GetMapping("/phases/payees/page")
    public Page<Phase> getPhasesPayeesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return reportingService.getPhasesPayeesPage(page, size);
    }
    @GetMapping("/phases/terminees-non-facturees/projet/{projetId}")
    public List<Phase> getPhasesTermineesNonFactureesByProjet(@PathVariable Long projetId) {
        return reportingService.getPhasesTermineesNonFactureesByProjet(projetId);
    }
    @GetMapping("/phases/terminees-non-facturees")
    public List<Phase> getPhasesTermineesNonFacturees() {
        return reportingService.getPhasesTermineesNonFacturees();
    }
    @GetMapping("/phases/facturees-non-payees/projet/{projetId}")
    public List<Phase> getPhasesFactureesNonPayeesByProjet(@PathVariable Long projetId) {
        return reportingService.getPhasesFactureesNonPayeesByProjet(projetId);
    }
    @GetMapping("/projets/clotures/chef/{chefProjetId}")
    public List<Projet> getProjetsCloturesByChef(@PathVariable Long chefProjetId) {
        return reportingService.getProjetsCloturesByChef(chefProjetId);
    }
    @GetMapping("/projets/en-cours/periode")
    public List<Projet> getProjetsEnCoursByPeriode(
            @RequestParam LocalDate dateDebut,
            @RequestParam LocalDate dateFin) {
        return reportingService.getProjetsEnCoursByPeriode(dateDebut, dateFin);
    }

    @GetMapping("/projets/clotures/periode")
    public List<Projet> getProjetsCloturesByPeriode(
            @RequestParam LocalDate dateDebut,
            @RequestParam LocalDate dateFin) {
        return reportingService.getProjetsCloturesByPeriode(dateDebut, dateFin);
    }
    @GetMapping("/phases/payees/projet/{projetId}")
    public List<Phase> getPhasesPayeesByProjet(@PathVariable Long projetId) {
        return reportingService.getPhasesPayeesByProjet(projetId);
    }
    @GetMapping("/phases/facturees-non-payees")
    public List<Phase> getPhasesFactureesNonPayees() {
        return reportingService.getPhasesFactureesNonPayees();
    }

    @GetMapping("/phases/payees")
    public List<Phase> getPhasesPayees() {
        return reportingService.getPhasesPayees();
    }

    @GetMapping("/projets/en-cours")
    public List<Projet> getProjetsEnCours() {
        return reportingService.getProjetsEnCours();
    }

    @GetMapping("/projets/clotures")
    public List<Projet> getProjetsClotures() {
        return reportingService.getProjetsClotures();
    }

    @GetMapping("/tableau-de-bord")
    public TableauDeBordDTO getTableauDeBord() {
        return reportingService.getTableauDeBord();
    }
}