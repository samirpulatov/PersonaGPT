package com.samirpulatov.persona_agent.backend.controller;


import com.samirpulatov.persona_agent.backend.dto.DocumentUploadResponse;
import com.samirpulatov.persona_agent.backend.repository.DocumentRepository;
import com.samirpulatov.persona_agent.backend.service.CustomUserDetails;
import com.samirpulatov.persona_agent.backend.service.DocumentService;
import com.samirpulatov.persona_agent.backend.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/documents/me")
public class DocumentsUploadController {

    private final DocumentService documentService;
    private final JwtService jwtService;
    private final DocumentRepository documentRepository;

    public DocumentsUploadController(DocumentService documentService, JwtService jwtService, DocumentRepository documentRepository) {
        this.documentService = documentService;
        this.jwtService = jwtService;
        this.documentRepository = documentRepository;
    }

    // Check if the user has already uploaded a document
    @GetMapping("/upload/hasDocument")
    public ResponseEntity<?> checkDocumentStatus(Authentication authentication) {
        CustomUserDetails authenticatedUser = (CustomUserDetails) authentication.getPrincipal();
        Integer userId = authenticatedUser.getId();
        if(authenticatedUser.getId() == null){
            return ResponseEntity.badRequest().body("User not found");
        }

        var hasDocument = documentRepository.existsByUserId(userId);
        return ResponseEntity.ok(Map.of("hasDocument",hasDocument));

    }

    @PostMapping("/upload/pdf")
    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file, Authentication authentication) {
        CustomUserDetails authenticatedUser = (CustomUserDetails) authentication.getPrincipal();
        Integer userId = authenticatedUser.getId();
        DocumentUploadResponse response;
        try {
            response = documentService.uploadPdf(file,userId);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading file");
        }
        return ResponseEntity.ok(response);
    }


}
