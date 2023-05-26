package com.example.pdp_esm.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pdp_esm.dto.result.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import com.example.pdp_esm.service.Implements.CertificateServiceImpl;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/certificate")
public class CertificateController {

    private final CertificateServiceImpl certificateService;

    @PreAuthorize(value = "hasAnyAuthority('MANAGER')")
    @PostMapping("/create/{id}")
    public ResponseEntity<?> createCertificate(@PathVariable Long id) {
        ApiResponse<?> response = certificateService.createCertificate(id);

        if (response.isSuccess()) log.warn("Creating Certificate! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/")
    public ResponseEntity<?> getAllCertificate() {
        ApiResponse<?> response = certificateService.getAllCertificate();

        if (response.isSuccess()) log.warn("Certificates List -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOneCertificate(@PathVariable Long id) {
        ApiResponse<?> response = certificateService.getOne(id);

        if (response.isSuccess()) log.warn("Getting One Certificate by {} id -> {}", id, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN', 'MANAGER')")
    @GetMapping("/getOne/{certificateId}")
    public ResponseEntity<?> getCertificate(@PathVariable String certificateId) {
        ApiResponse<?> response = certificateService.getCertificateById(certificateId);

        if (response.isSuccess()) log.warn("Getting One Certificate by {} id -> {}", certificateId, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('MANAGER')")
    @DeleteMapping("/deleteCertificate/{id}")
    public ResponseEntity<?> deleteCertificate(@PathVariable Long id) {
        ApiResponse<?> response = certificateService.deleteCertificate(id);

        if (response.isSuccess()) log.warn("Deleting Certificate by {} id -> {}", id, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}