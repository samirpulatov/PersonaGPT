package com.samirpulatov.persona_agent.backend.service;

import ch.qos.logback.core.util.StringUtil;
import com.samirpulatov.persona_agent.backend.entity.Document;
import com.samirpulatov.persona_agent.backend.entity.User;
import com.samirpulatov.persona_agent.backend.enums.DocumentStatus;
import com.samirpulatov.persona_agent.backend.repository.DocumentRepository;
import com.samirpulatov.persona_agent.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class DocumentService {

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Value("${spring.app.upload.dir}")
    private String uploadDir;



    public void uploadPdf(MultipartFile file, Long userId) throws IOException {
        validateDocument(file);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        //Create a path reference
        // toAbsolutePath() - Convert a relative path to a full path
        // normalize() - removes redundant elements like . or ...
        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();

        //Create directories
        Files.createDirectories(uploadPath);

        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String storedFilename = generateStoredFilename(originalFilename);

        //Append a partial path to a current one
        Path targetPath = uploadPath.resolve(storedFilename);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        Document document = new Document();
        document.setUserId(user.getId());
        document.setOriginalFilename(originalFilename);
        document.setStoragePath(targetPath.toString());
        document.setMimeType(file.getContentType());
        document.setFileSize(file.getSize());
        document.setStatus(DocumentStatus.UPLOADED);
        document.setCreatedAt(LocalDateTime.now());
        documentRepository.save(document);
    }




    public DocumentService(UserRepository userRepository, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }

    private void validateDocument(MultipartFile file){
        if(file == null || file.isEmpty()){
            throw new IllegalArgumentException("File is empty or null");
        }

        String fileName = file.getOriginalFilename();
        if(fileName == null || !fileName.toLowerCase().endsWith(".pdf")){
            throw new IllegalArgumentException("File must be a PDF");
        }

        String contentType = file.getContentType();
        if(contentType == null || !contentType.equalsIgnoreCase("application/pdf")){}
        throw new IllegalArgumentException("File must be a PDF");
    }

    private String generateStoredFilename(String originalFilename) {
        String extension = "";

        int dotIndex = originalFilename.lastIndexOf('.');
        if(dotIndex>=0){
            extension = originalFilename.substring(dotIndex);
        }

        return UUID.randomUUID() + extension;

    }
}
