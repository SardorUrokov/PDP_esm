package com.example.pdp_esm.service.Implements;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

@Service
@RequiredArgsConstructor
public class CertificateDownloader {
    private static final String CERTIFICATES_PATH = "C:\\Users\\user\\Desktop\\PDP_Certificates";

    public ResponseEntity<byte[]> downloadCertificate(String certificateName) {
        String filePath = CERTIFICATES_PATH + File.separator + certificateName + ".pdf";

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
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}