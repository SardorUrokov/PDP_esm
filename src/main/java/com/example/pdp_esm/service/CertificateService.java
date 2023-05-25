package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.result.ApiResponse;

public interface CertificateService {

    ApiResponse<?> createCertificate(Long student_id);

    ApiResponse<?> getAllCertificate();

    ApiResponse<?> getOne(Long id);

    ApiResponse<?> getCertificateById(String id);

    ApiResponse<?> deleteCertificate(Long id);

}