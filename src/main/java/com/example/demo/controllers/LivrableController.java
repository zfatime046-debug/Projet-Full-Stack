package com.example.demo.controllers;

import com.example.demo.dto.LivrableRequestDTO;
import com.example.demo.dto.LivrableResponseDTO;
import com.example.demo.services.LivrableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LivrableController {

    private final LivrableService livrableService;

    // POST /api/phases/{phaseId}/livrables
    @PostMapping("/api/phases/{phaseId}/livrables")
    public ResponseEntity<LivrableResponseDTO> create(
            @PathVariable Long phaseId,
            @RequestBody LivrableRequestDTO dto) {
        return ResponseEntity.ok(livrableService.create(phaseId, dto));
    }

    // GET /api/phases/{phaseId}/livrables
    @GetMapping("/api/phases/{phaseId}/livrables")
    public ResponseEntity<List<LivrableResponseDTO>> getByPhase(@PathVariable Long phaseId) {
        return ResponseEntity.ok(livrableService.getByPhase(phaseId));
    }

    // GET /api/livrables/{id}
    @GetMapping("/api/livrables/{id}")
    public ResponseEntity<LivrableResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(livrableService.getById(id));
    }

    // PUT /api/livrables/{id}
    @PutMapping("/api/livrables/{id}")
    public ResponseEntity<LivrableResponseDTO> update(
            @PathVariable Long id,
            @RequestBody LivrableRequestDTO dto) {
        return ResponseEntity.ok(livrableService.update(id, dto));
    }

    // DELETE /api/livrables/{id}
    @DeleteMapping("/api/livrables/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livrableService.delete(id);
        return ResponseEntity.noContent().build();
    }
}