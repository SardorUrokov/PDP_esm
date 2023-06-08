package com.example.pdp_esm.service.Implements;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class CertificateDownloader {

    private static final String CERTIFICATES_PATH = "C:\\Users\\user\\Desktop\\PDP_Certificates";

    public ResponseEntity<byte[]> downloadCertificate(String certificateName) {
        String filePath = CERTIFICATES_PATH + File.separator + certificateName;

        try {
            Path certificatePath = Paths.get(filePath);
            byte[] certificateBytes = Files.readAllBytes(certificatePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", certificateName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(certificateBytes);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception and return an appropriate response
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

}