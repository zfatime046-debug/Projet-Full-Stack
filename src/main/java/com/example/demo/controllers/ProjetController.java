package com.example.demo.controllers;

import com.example.demo.dto.ProjetRequest;
import com.example.demo.entities.Projet;
import com.example.demo.services.ProjetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin("*")
public class ProjetController {

    private final ProjetService projetService;

    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @PostMapping
    public Projet createProjet(@RequestBody ProjetRequest request) {
        return projetService.createProjet(request);
    }

    @PutMapping("/{id}")
    public Projet updateProjet(@PathVariable Long id, @RequestBody ProjetRequest request) {
        return projetService.updateProjet(id, request);
    }

    @GetMapping("/{id}")
    public Projet getProjetById(@PathVariable Long id) {
        return projetService.getProjetById(id);
    }

    @GetMapping
    public List<Projet> getAllProjets() {
        return projetService.getAllProjets();
    }

    @GetMapping("/organisme/{organismeId}")
    public List<Projet> getProjetsByOrganisme(@PathVariable Long organismeId) {
        return projetService.getProjetsByOrganisme(organismeId);
    }

    @GetMapping("/chef/{employeId}")
    public List<Projet> getProjetsByChefDeProjet(@PathVariable Long employeId) {
        return projetService.getProjetsByChefDeProjet(employeId);
    }

    @DeleteMapping("/{id}")
    public void deleteProjet(@PathVariable Long id) {
        projetService.deleteProjet(id);
    }
}