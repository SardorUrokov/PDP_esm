package com.example.pdp_esm.controller;

import com.example.pdp_esm.service.Implements.MinioService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pdp_esm.service.Implements.CertificateDownloader;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/download")
public class DownloadController {

    private final CertificateDownloader certificateDownloader;
    private final MinioService minioService;

    @GetMapping("/{certificateId}")
    public ResponseEntity<byte[]> getCertificate(@PathVariable String certificateId) {
        final var response = certificateDownloader.downloadCertificate(certificateId);

        log.warn("Certificate downloading! -> {}", certificateId);

        return response;
    }

    @GetMapping("/minio/{certificateId}")
    public ResponseEntity<byte[]> getCertificateFromMinio(@PathVariable String certificateId) {
        final var response = minioService.downloadFile(certificateId);

        log.warn("Certificate downloading from Minio! -> {}", certificateId);

        return response;
    }
}