package com.example.demo.controllers;

import com.example.demo.dto.DocumentDTO;
import com.example.demo.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api") // ✅ IMPORTANT : base clean
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(value = "/projets/{projetId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETAIRE', 'CHEF')")
    public ResponseEntity<DocumentDTO> createDocument(
            @PathVariable Long projetId,
            @RequestPart("file") MultipartFile file,
            @RequestPart("data") DocumentDTO documentDTO
    ) throws IOException {

        return ResponseEntity.ok(
                documentService.createDocument(projetId, documentDTO, file)
        );
    }

    @GetMapping("/projets/{projetId}/documents")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DocumentDTO>> getDocumentsByProjet(@PathVariable Long projetId) {
        return ResponseEntity.ok(documentService.getDocumentsByProjet(projetId));
    }

    @GetMapping("/documents/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DocumentDTO> getDocumentById(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    @DeleteMapping("/documents/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETAIRE', 'CHEF')")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) throws IOException {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/documents/{id}/download")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id)
            throws MalformedURLException {

        Resource resource = documentService.downloadDocument(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}