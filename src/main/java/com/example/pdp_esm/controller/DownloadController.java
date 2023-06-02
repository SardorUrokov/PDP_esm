package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.CertificateDownloader;
import com.example.pdp_esm.service.Implements.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/download")
public class DownloadController {

    private final FileStorageService storageService;
    private final CertificateDownloader certificateDownloader;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        ApiResponse<?> response = storageService.uploadFile(file);

        if (response.isSuccess()) log.warn("Certificate Uploaded {} ! -> {}" , file.getName(), response);
        else log.error(response.getMessage());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{certificateId}")
    public ResponseEntity<byte[]> getCertificate(@PathVariable String certificateName) {
        return certificateDownloader.downloadCertificate(certificateName);
    }
}