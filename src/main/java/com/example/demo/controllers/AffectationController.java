package com.example.demo.controllers;

import com.example.demo.dto.AffectationRequest;
import com.example.demo.entities.Affectation;
import com.example.demo.entities.Phase;
import com.example.demo.services.AffectationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affectations")
@CrossOrigin("*")
public class AffectationController {

    private final AffectationService affectationService;

    public AffectationController(AffectationService affectationService) {
        this.affectationService = affectationService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public Affectation createAffectation(@RequestBody AffectationRequest request) {
        return affectationService.createAffectation(request);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public Affectation updateAffectation(@RequestBody AffectationRequest request) {
        return affectationService.updateAffectation(request);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Affectation> getAllAffectations() {
        return affectationService.getAllAffectations();
    }

    @GetMapping("/employe/{employeId}/phases")
    @PreAuthorize("isAuthenticated()")
    public List<Phase> getPhasesByEmploye(@PathVariable Long employeId) {
        return affectationService.getPhasesByEmploye(employeId);
    }

    @GetMapping("/{employeId}/{phaseId}")
    @PreAuthorize("isAuthenticated()")
    public Affectation getAffectation(@PathVariable Long employeId, @PathVariable Long phaseId) {
        return affectationService.getAffectation(employeId, phaseId);
    }

    @GetMapping("/phase/{phaseId}")
    @PreAuthorize("isAuthenticated()")
    public List<Affectation> getAffectationsByPhase(@PathVariable Long phaseId) {
        return affectationService.getAffectationsByPhase(phaseId);
    }

    @GetMapping("/employe/{employeId}")
    @PreAuthorize("isAuthenticated()")
    public List<Affectation> getAffectationsByEmploye(@PathVariable Long employeId) {
        return affectationService.getAffectationsByEmploye(employeId);
    }

    @DeleteMapping("/{employeId}/{phaseId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public void deleteAffectation(@PathVariable Long employeId, @PathVariable Long phaseId) {
        affectationService.deleteAffectation(employeId, phaseId);
    }
}