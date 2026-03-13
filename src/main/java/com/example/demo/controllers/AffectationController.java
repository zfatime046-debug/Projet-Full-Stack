package com.example.demo.controllers;

import com.example.demo.dto.AffectationRequest;
import com.example.demo.entities.Affectation;
import com.example.demo.services.AffectationService;
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
    public Affectation createAffectation(@RequestBody AffectationRequest request) {
        return affectationService.createAffectation(request);
    }

    @PutMapping
    public Affectation updateAffectation(@RequestBody AffectationRequest request) {
        return affectationService.updateAffectation(request);
    }

    @GetMapping
    public List<Affectation> getAllAffectations() {
        return affectationService.getAllAffectations();
    }

    @GetMapping("/{employeId}/{phaseId}")
    public Affectation getAffectation(@PathVariable Long employeId, @PathVariable Long phaseId) {
        return affectationService.getAffectation(employeId, phaseId);
    }

    @GetMapping("/phase/{phaseId}")
    public List<Affectation> getAffectationsByPhase(@PathVariable Long phaseId) {
        return affectationService.getAffectationsByPhase(phaseId);
    }

    @GetMapping("/employe/{employeId}")
    public List<Affectation> getAffectationsByEmploye(@PathVariable Long employeId) {
        return affectationService.getAffectationsByEmploye(employeId);
    }

    @DeleteMapping("/{employeId}/{phaseId}")
    public void deleteAffectation(@PathVariable Long employeId, @PathVariable Long phaseId) {
        affectationService.deleteAffectation(employeId, phaseId);
    }
}