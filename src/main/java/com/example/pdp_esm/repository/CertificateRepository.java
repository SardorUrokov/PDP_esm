package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByCertificateNumber(String certificateNUmber);

    Optional<Certificate> findByStudentId(Long student_id);

}