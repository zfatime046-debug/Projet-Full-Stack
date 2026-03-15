package com.example.demo.controllers;

import com.example.demo.dto.EmployeRequestDTO;
import com.example.demo.dto.EmployeResponseDTO;
import com.example.demo.entities.Employe;
import com.example.demo.services.EmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeResponseDTO> creer(@RequestBody EmployeRequestDTO dto) {
        return ResponseEntity.ok(employeService.creer(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeResponseDTO> modifier(@PathVariable Long id,
                                                       @RequestBody EmployeRequestDTO dto) {
        return ResponseEntity.ok(employeService.modifier(id, dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTEUR', 'CHEF')")
    public ResponseEntity<EmployeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTEUR', 'CHEF')")
    public ResponseEntity<List<EmployeResponseDTO>> getAll() {
        return ResponseEntity.ok(employeService.getAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        employeService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibles")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECTEUR', 'CHEF')")
    public ResponseEntity<List<Employe>> getEmployesDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        List<Employe> employes = employeService.getEmployesDisponibles(dateDebut, dateFin);
        return ResponseEntity.ok(employes);
    }

}