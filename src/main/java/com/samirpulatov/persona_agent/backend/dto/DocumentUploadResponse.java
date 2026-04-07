package com.samirpulatov.persona_agent.backend.dto;

public record DocumentUploadResponse(
        Integer documentId,
        String originalFilename,
        String status,
        String message
){

}
