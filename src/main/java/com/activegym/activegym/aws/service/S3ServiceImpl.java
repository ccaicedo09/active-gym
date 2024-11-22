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

/**
 * Service implementation for interacting with AWS S3 to handle file uploads and deletions.
 * This service uses the AWS SDK for Java to perform operations on S3.
 * <p>
 * Provides methods to upload files to an S3 bucket and delete files from it.
 * </p>
 *
 * @author Carlos Esteban Castro Caicedo
 * @since v1.3
 */
@Service
@AllArgsConstructor
public class S3ServiceImpl {

    private final S3Client s3Client;
    private final String s3Bucket;

    /**
     * Uploads a file to the configured S3 bucket.
     * <p>
     * Generates a unique file name using a UUID to prevent conflicts and appends
     * the original file name to it. The file is then uploaded to the S3 bucket.
     * </p>
     *
     * @param file the {@link MultipartFile} to upload.
     * @return the public URL of the uploaded file in the S3 bucket.
     * @throws IOException if there is an issue reading the file or uploading it to S3.
     */
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

    /**
     * Deletes a file from the configured S3 bucket.
     * <p>
     * If the provided path corresponds to the default profile picture, the method
     * exits without performing any operations. Otherwise, it parses the file key
     * from the given path and sends a delete request to the S3 service.
     * </p>
     *
     * @param path the public URL of the file to delete.
     * @throws IOException if there is an issue deleting the file from S3.
     */
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
