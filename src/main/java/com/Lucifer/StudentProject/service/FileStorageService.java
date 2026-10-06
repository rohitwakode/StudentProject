package com.Lucifer.StudentProject.service;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService  {

    @Value("${file.upload-dir}")
    private String uploadDir;

    String storeFile(MultipartFile file)throws IOException {

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {

            Files.createDirectories(uploadPath);

        }
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";
        int dotINdex = originalFilename.lastIndexOf(".");

        if (dotINdex >0 ) {
            fileExtension = originalFilename.substring(dotINdex);
        }
        String filename= UUID.randomUUID()+fileExtension;
        Path targetLocation=uploadPath.resolve(filename);
        Files.copy(
                file.getInputStream(),
                targetLocation,
                StandardCopyOption.REPLACE_EXISTING
        );
        return filename;
    }
}
