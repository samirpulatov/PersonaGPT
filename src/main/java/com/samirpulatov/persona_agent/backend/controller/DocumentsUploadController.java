package com.samirpulatov.persona_agent.backend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents/upload")
public class DocumentsUploadController {

    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file) {



    }
}
