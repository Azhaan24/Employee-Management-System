package com.project.ems.service.impl;

import com.project.ems.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            if(file.isEmpty()) {
                return "default.png";
            }
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(),Paths.get(uploadDir + fileName),StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}