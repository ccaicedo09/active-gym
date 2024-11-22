package com.activegym.activegym.aws.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Configuration class for S3 client. Provides de S3 client bean adding the AWS credentials
 * for uploading files to the S3 bucket.
 * @since v1.3
 * @author Carlos Esteban Castro Caicedo
 */
@Configuration
public class S3Config {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    protected String awsAccessKey() {
        return dotenv.get("AWS_ACCESS_KEY");
    }

    @Bean
    protected String awsSecretKey() {
        return dotenv.get("AWS_SECRET_KEY");
    }

    @Bean
    protected String awsRegion() {
        return dotenv.get("AWS_REGION");
    }

    @Bean
    public String s3Bucket() {
        return dotenv.get("BUCKET_NAME");
    }

    @Bean
    public S3Client s3Client() {
        String accessKey = awsAccessKey();
        String secretKey = awsSecretKey();
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of(awsRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
