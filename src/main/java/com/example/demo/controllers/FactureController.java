package com.example.demo.controllers;

import com.example.demo.dto.FactureDTO;
import com.example.demo.services.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;

    @PostMapping("/phases/{phaseId}/facture")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMPTABLE')")
    public ResponseEntity<FactureDTO> createFacture(
            @PathVariable Long phaseId,
            @RequestBody FactureDTO dto) {
        return ResponseEntity.ok(factureService.createFacture(phaseId, dto));
    }

    @GetMapping("/factures")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FactureDTO>> getAllFactures() {
        return ResponseEntity.ok(factureService.getAllFactures());
    }

    @GetMapping("/factures/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FactureDTO> getFactureById(@PathVariable Long id) {
        return ResponseEntity.ok(factureService.getFactureById(id));
    }

    @PutMapping("/factures/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMPTABLE')")
    public ResponseEntity<FactureDTO> updateFacture(
            @PathVariable Long id,
            @RequestBody FactureDTO dto) {
        return ResponseEntity.ok(factureService.updateFacture(id, dto));
    }

    @DeleteMapping("/factures/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMPTABLE')")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }

    // 🔥 FIX IMPORTANT STATUT
    @PutMapping("/factures/{id}/statut")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMPTABLE')")
    public ResponseEntity<FactureDTO> updateStatut(
            @PathVariable Long id,
            @RequestBody FactureDTO dto
    ) {
        return ResponseEntity.ok(factureService.updateStatut(id, dto));
    }
}