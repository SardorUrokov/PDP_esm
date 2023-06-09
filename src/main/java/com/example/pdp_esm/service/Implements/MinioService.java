package com.example.pdp_esm.service.Implements;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import io.minio.GetObjectArgs;
import io.minio.errors.MinioException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.BucketExistsArgs;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService {
    private static final String ACCESS_KEY = "minioadmin";
    private static final String SECRET_KEY = "minioadmin";
    private static final String ENDPOINT = "http://localhost:9000";
    private static final String CERTIFICATES_PATH = "C:\\Users\\user\\Desktop\\PDP_Certificates\\";
    private static final String BUCKET_NAME = "my-bucket";
//    private static final String DESTINATION_PATH = "C:\\Users\\user\\Desktop\\PDP_Certificates\\DownloadedMinio";

    public void uploadMinio(String objectName) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();


            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(BUCKET_NAME)
                            .build());
            if (!bucketExists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(BUCKET_NAME)
                                .build());
            }

            File file = new File(CERTIFICATES_PATH + objectName);
            InputStream inputStream = new FileInputStream(file);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(objectName)
                            .stream(inputStream, file.length(), -1)
                            .build());

            log.info("File successfully uploaded to Minio with name -> {}!", objectName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<byte[]> downloadFile(String object_name) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();

            // Download the file from MinIO
            InputStream inputStream = null;
            try {
                inputStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(BUCKET_NAME)
                                .object(object_name)
                                .build()
                );
            } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            // Convert the input stream to byte array
            byte[] fileBytes = inputStream.readAllBytes();

            // Set the appropriate response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", object_name);

            // Return the file bytes in the response
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileBytes);

        } catch (MinioException | IOException e) {
            e.printStackTrace();
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}