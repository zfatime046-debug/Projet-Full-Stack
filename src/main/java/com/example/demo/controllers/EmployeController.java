package com.example.demo.controllers;

import com.example.demo.dto.EmployeRequestDTO;
import com.example.demo.dto.EmployeResponseDTO;
import com.example.demo.services.EmployeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeResponseDTO> creer(@Valid @RequestBody EmployeRequestDTO dto) {
        return ResponseEntity.ok(employeService.creer(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeResponseDTO> modifier(@PathVariable Long id, @Valid @RequestBody EmployeRequestDTO dto) {
        return ResponseEntity.ok(employeService.modifier(id, dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EmployeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.getById(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<EmployeResponseDTO>> getAll() {
        return ResponseEntity.ok(employeService.getAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        employeService.supprimer(id);
        return ResponseEntity.noContent().build();
    }


}