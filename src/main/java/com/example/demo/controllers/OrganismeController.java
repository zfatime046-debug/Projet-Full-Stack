package com.example.demo.controllers;

import com.example.demo.dto.OrganismeRequestDTO;
import com.example.demo.dto.OrganismeResponseDTO;
import com.example.demo.services.OrganismeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organismes")
@RequiredArgsConstructor
public class OrganismeController {

    private final OrganismeService organismeService;

    @PostMapping
    public ResponseEntity<OrganismeResponseDTO> creer(@Valid @RequestBody OrganismeRequestDTO dto) {
        return ResponseEntity.ok(organismeService.creer(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganismeResponseDTO> modifier(@PathVariable Long id,
                                                         @Valid @RequestBody OrganismeRequestDTO dto) {
        return ResponseEntity.ok(organismeService.modifier(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganismeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(organismeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrganismeResponseDTO>> getAll() {
        return ResponseEntity.ok(organismeService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        organismeService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recherche/nom")
    public ResponseEntity<List<OrganismeResponseDTO>> rechercherParNom(@RequestParam String nom) {
        return ResponseEntity.ok(organismeService.rechercherParNom(nom));
    }

    @GetMapping("/recherche/code")
    public ResponseEntity<OrganismeResponseDTO> rechercherParCode(@RequestParam String code) {
        return ResponseEntity.ok(organismeService.rechercherParCode(code));
    }

    @GetMapping("/recherche/contact")
    public ResponseEntity<List<OrganismeResponseDTO>> rechercherParContact(@RequestParam String contact) {
        return ResponseEntity.ok(organismeService.rechercherParContact(contact));
    }
}