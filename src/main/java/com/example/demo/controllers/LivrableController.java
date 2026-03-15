package com.example.demo.controllers;

import com.example.demo.dto.LivrableRequestDTO;
import com.example.demo.dto.LivrableResponseDTO;
import com.example.demo.services.LivrableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LivrableController {

    private final LivrableService livrableService;

    @PostMapping("/api/phases/{phaseId}/livrables")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<LivrableResponseDTO> create(
            @PathVariable Long phaseId,
            @RequestBody LivrableRequestDTO dto) {
        return ResponseEntity.ok(livrableService.create(phaseId, dto));
    }

    @GetMapping("/api/phases/{phaseId}/livrables")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LivrableResponseDTO>> getByPhase(@PathVariable Long phaseId) {
        return ResponseEntity.ok(livrableService.getByPhase(phaseId));
    }

    @GetMapping("/api/livrables/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LivrableResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(livrableService.getById(id));
    }

    @PutMapping("/api/livrables/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<LivrableResponseDTO> update(
            @PathVariable Long id,
            @RequestBody LivrableRequestDTO dto) {
        return ResponseEntity.ok(livrableService.update(id, dto));
    }

    @DeleteMapping("/api/livrables/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livrableService.delete(id);
        return ResponseEntity.noContent().build();
    }
}