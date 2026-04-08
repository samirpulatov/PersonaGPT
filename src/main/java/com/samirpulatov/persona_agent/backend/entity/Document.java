package com.samirpulatov.persona_agent.backend.entity;

import com.samirpulatov.persona_agent.backend.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Column(name = "mime_type",nullable = false)
    private String mimeType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DocumentStatus status;

    @Column(name = "raw_text", columnDefinition = "LONGTEXT")
    private String rawText;

    @Column(name = "parsed_json", columnDefinition = "json")
    private String parsedJson;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "parsed_at")
    private LocalDateTime parsedAt;

    public Document(DocumentBuilder builder) {
        this.userId = builder.userId;
        this.originalFilename = builder.originalFilename;
        this.storagePath = builder.storagePath;
        this.mimeType = builder.mimeType;
        this.fileSize = builder.fileSize;
        this.status = builder.status;
        this.rawText = builder.rawText;
        this.parsedJson = builder.parsedJson;
        this.createdAt = builder.createdAt;
    }


    public static class DocumentBuilder {
        private Integer userId;
        private String originalFilename;
        private String storagePath;
        private String mimeType;
        private Long fileSize;
        private DocumentStatus status;
        private String rawText;
        private String parsedJson;
        private LocalDateTime createdAt;

        public DocumentBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public DocumentBuilder originalFilename(String originalFilename) {
            this.originalFilename = originalFilename;
            return this;
        }

        public DocumentBuilder storagePath(String storagePath) {
            this.storagePath = storagePath;
            return this;
        }

        public DocumentBuilder mimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public DocumentBuilder fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public DocumentBuilder status(DocumentStatus status) {
            this.status = status;
            return this;
        }

        public DocumentBuilder rawText(String rawText) {
            this.rawText = rawText;
            return this;
        }

        public DocumentBuilder parsedJson(String parsedJson) {
            this.parsedJson = parsedJson;
            return this;
        }

        public DocumentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Document build() {
            return new Document(this);
        }
    }

}
