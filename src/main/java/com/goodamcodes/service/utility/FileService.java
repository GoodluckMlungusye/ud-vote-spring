package com.goodamcodes.service.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;

@Service
public class FileService {

    @Value("${url.image}")
    private String imageStoragePath;

    @Value("${url.video}")
    private String videoStoragePath;

    @Value("${url.document}")
    private String documentStoragePath;


    private static final Set<String> IMAGE_EXTENSIONS = Set.of("png", "jpg", "jpeg", "gif", "bmp", "tiff", "svg");
    private static final Set<String> VIDEO_EXTENSIONS = Set.of("mp4", "mov", "avi", "mkv", "flv", "wmv", "webm");
    private static final Set<String> DOCUMENT_EXTENSIONS = Set.of("pdf", "doc", "docx", "txt", "rtf", "odt", "xls", "xlsx");

    public String saveFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File must have a valid name.");
        }

        String safeFileName = Path.of(originalFileName).getFileName().toString();
        String extension = getFileExtension(safeFileName).toLowerCase();
        String targetDirectory = getStorageDirectory(extension);

        try {
            Path directoryPath = Paths.get(targetDirectory);
            Files.createDirectories(directoryPath);

            Path targetPath = directoryPath.resolve(safeFileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return safeFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + safeFileName, e);
        }
    }


    public void deleteFile(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        String targetDirectory = getStorageDirectory(extension);

        try {
            Path filePath = Paths.get(targetDirectory, filename);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            } else {
                throw new RuntimeException("File not found: " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + filename, e);
        }
    }

    private String getFileExtension(String fileName) {
        int idx = fileName.lastIndexOf(".");
        if (idx == -1 || idx == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(idx + 1);
    }

    private String getStorageDirectory(String extension) {
        if (IMAGE_EXTENSIONS.contains(extension)) {
            return imageStoragePath;
        } else if (VIDEO_EXTENSIONS.contains(extension)) {
            return videoStoragePath;
        } else if (DOCUMENT_EXTENSIONS.contains(extension)) {
            return documentStoragePath;
        } else {
            throw new IllegalArgumentException("Unsupported file extension: " + extension);
        }
    }
}
