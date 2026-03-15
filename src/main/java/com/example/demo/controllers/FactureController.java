package com.example.demo.controllers;

import com.example.demo.dto.FactureDTO;
import com.example.demo.services.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;

    // POST /api/phases/{phaseId}/facture
    @PostMapping("/api/phases/{phaseId}/facture")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMPTABLE')")
    public ResponseEntity<FactureDTO> createFacture(
            @PathVariable Long phaseId,
            @RequestBody FactureDTO dto) {
        return ResponseEntity.ok(factureService.createFacture(phaseId, dto));
    }

    // GET /api/factures
    @GetMapping("/api/factures")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FactureDTO>> getAllFactures() {
        return ResponseEntity.ok(factureService.getAllFactures());
    }

    // GET /api/factures/{id}
    @GetMapping("/api/factures/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FactureDTO> getFactureById(@PathVariable Long id) {
        return ResponseEntity.ok(factureService.getFactureById(id));
    }

    // PUT /api/factures/{id}
    @PutMapping("/api/factures/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMPTABLE')")
    public ResponseEntity<FactureDTO> updateFacture(
            @PathVariable Long id,
            @RequestBody FactureDTO dto) {
        return ResponseEntity.ok(factureService.updateFacture(id, dto));
    }

    // DELETE /api/factures/{id}
    @DeleteMapping("/api/factures/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'COMPTABLE')")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }
}