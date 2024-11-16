package com.activegym.activegym.aws.controller;

import com.activegym.activegym.aws.service.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class S3Controller {
    private final S3ServiceImpl s3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            return s3Service.uploadFile(file);
        } catch (Exception e) {
            return "Error uploading file: " + e.getMessage();
        }
    }
}
