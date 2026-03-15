package com.example.demo.controllers;

import com.example.demo.dto.PhaseRequest;
import com.example.demo.entities.Phase;
import com.example.demo.services.PhaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phases")
@CrossOrigin("*")
public class PhaseController {

    private final PhaseService phaseService;

    public PhaseController(PhaseService phaseService) {
        this.phaseService = phaseService;
    }
    @PatchMapping("/{id}/realisation")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public Phase updateEtatRealisation(@PathVariable Long id, @RequestBody Boolean etat) {
        return phaseService.updateEtatRealisation(id, etat);
    }

    @PatchMapping("/{id}/facturation")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMPTABLE')")
    public Phase updateEtatFacturation(@PathVariable Long id, @RequestBody Boolean etat) {
        return phaseService.updateEtatFacturation(id, etat);
    }

    @PatchMapping("/{id}/paiement")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMPTABLE')")
    public Phase updateEtatPaiement(@PathVariable Long id, @RequestBody Boolean etat) {
        return phaseService.updateEtatPaiement(id, etat);
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public Phase createPhase(@RequestBody PhaseRequest request) {
        return phaseService.createPhase(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public Phase updatePhase(@PathVariable Long id, @RequestBody PhaseRequest request) {
        return phaseService.updatePhase(id, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Phase getPhaseById(@PathVariable Long id) {
        return phaseService.getPhaseById(id);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Phase> getAllPhases() {
        return phaseService.getAllPhases();
    }

    @GetMapping("/projet/{projetId}")
    @PreAuthorize("isAuthenticated()")
    public List<Phase> getPhasesByProjet(@PathVariable Long projetId) {
        return phaseService.getPhasesByProjet(projetId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public void deletePhase(@PathVariable Long id) {
        phaseService.deletePhase(id);
    }
}