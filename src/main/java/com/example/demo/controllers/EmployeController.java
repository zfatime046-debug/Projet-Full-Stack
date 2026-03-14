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

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    @PostMapping
    public ResponseEntity<EmployeResponseDTO> creer(@RequestBody EmployeRequestDTO dto) {
        return ResponseEntity.ok(employeService.creer(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeResponseDTO> modifier(@PathVariable Long id,
                                                       @RequestBody EmployeRequestDTO dto) {
        return ResponseEntity.ok(employeService.modifier(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeResponseDTO>> getAll() {
        return ResponseEntity.ok(employeService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        employeService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Employe>> getEmployesDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        List<Employe> employes = employeService.getEmployesDisponibles(dateDebut, dateFin);
        return ResponseEntity.ok(employes);
    }
}