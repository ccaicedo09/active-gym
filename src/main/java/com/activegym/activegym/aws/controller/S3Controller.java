package com.activegym.activegym.aws.controller;

import com.activegym.activegym.aws.service.S3ServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "S3 Test Controller", description = "S3 controller for testing. Not used in production.")
public class S3Controller {
    private final S3ServiceImpl s3Service;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Upload file to S3 bucket")
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            return s3Service.uploadFile(file);
        } catch (Exception e) {
            return "Error uploading file: " + e.getMessage();
        }
    }
}
