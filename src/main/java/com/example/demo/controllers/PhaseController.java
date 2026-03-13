package com.example.demo.controllers;

import com.example.demo.dto.PhaseRequest;
import com.example.demo.entities.Phase;
import com.example.demo.services.PhaseService;
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

    @PostMapping
    public Phase createPhase(@RequestBody PhaseRequest request) {
        return phaseService.createPhase(request);
    }

    @PutMapping("/{id}")
    public Phase updatePhase(@PathVariable Long id, @RequestBody PhaseRequest request) {
        return phaseService.updatePhase(id, request);
    }

    @GetMapping("/{id}")
    public Phase getPhaseById(@PathVariable Long id) {
        return phaseService.getPhaseById(id);
    }

    @GetMapping
    public List<Phase> getAllPhases() {
        return phaseService.getAllPhases();
    }

    @GetMapping("/projet/{projetId}")
    public List<Phase> getPhasesByProjet(@PathVariable Long projetId) {
        return phaseService.getPhasesByProjet(projetId);
    }

    @DeleteMapping("/{id}")
    public void deletePhase(@PathVariable Long id) {
        phaseService.deletePhase(id);
    }
}