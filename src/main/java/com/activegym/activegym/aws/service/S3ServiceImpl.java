package com.activegym.activegym.aws.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class S3ServiceImpl {

    private final S3Client s3Client;
    private final String s3Bucket;

    public String uploadFile(MultipartFile file) throws IOException {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(s3Bucket)
                    .key(fileName)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return "https://active-gym-bucket.s3.us-east-2.amazonaws.com/" + fileName.replace(" ", "%20");
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void deleteFile(String path) throws IOException {

        if (Objects.equals(path, "https://active-gym-bucket.s3.us-east-2.amazonaws.com/default-profile-picture.png")) {
            return;
        }

        try {
            String fileName = path.replace("https://active-gym-bucket.s3.us-east-2.amazonaws.com/", "");
            String key = fileName.replace("%20", " ");

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(s3Bucket)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new IOException("Error deleting the file: " + e.getMessage(), e);
        }
    }
}
