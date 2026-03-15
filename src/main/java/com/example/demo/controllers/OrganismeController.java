package com.example.demo.controllers;

import com.example.demo.dto.OrganismeRequestDTO;
import com.example.demo.dto.OrganismeResponseDTO;
import com.example.demo.services.OrganismeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organismes")
@RequiredArgsConstructor
public class OrganismeController {

    private final OrganismeService organismeService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETAIRE')")
    public ResponseEntity<OrganismeResponseDTO> creer(@Valid @RequestBody OrganismeRequestDTO dto) {
        return ResponseEntity.ok(organismeService.creer(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETAIRE')")
    public ResponseEntity<OrganismeResponseDTO> modifier(@PathVariable Long id,
                                                         @Valid @RequestBody OrganismeRequestDTO dto) {
        return ResponseEntity.ok(organismeService.modifier(id, dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrganismeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(organismeService.getById(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrganismeResponseDTO>> getAll() {
        return ResponseEntity.ok(organismeService.getAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETAIRE')")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        organismeService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recherche/nom")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrganismeResponseDTO>> rechercherParNom(@RequestParam String nom) {
        return ResponseEntity.ok(organismeService.rechercherParNom(nom));
    }

    @GetMapping("/recherche/code")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrganismeResponseDTO> rechercherParCode(@RequestParam String code) {
        return ResponseEntity.ok(organismeService.rechercherParCode(code));
    }

    @GetMapping("/recherche/contact")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrganismeResponseDTO>> rechercherParContact(@RequestParam String contact) {
        return ResponseEntity.ok(organismeService.rechercherParContact(contact));
    }
}