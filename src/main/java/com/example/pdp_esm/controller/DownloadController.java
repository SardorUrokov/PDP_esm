package com.example.pdp_esm.controller;

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

    @GetMapping("/{certificateId}")
    public ResponseEntity<byte[]> getCertificate(@PathVariable String certificateId) {
        return certificateDownloader.downloadCertificate(certificateId);
    }
}