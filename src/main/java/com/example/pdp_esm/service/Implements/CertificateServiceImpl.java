package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResCertificateDTO;
import com.example.pdp_esm.entity.Certificate;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.CertificateRepository;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final StudentRepository studentRepository;


    @Override
    public ApiResponse<?> createCertificate(Long student_id) {

        final var student = studentRepository.findById(student_id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student", "id", student_id));

        final var byId = certificateRepository.findById(student_id);
// fix this -> qayta saqlanmasin
        if (byId.isEmpty()) {
            final var course = student.getGroup().getCourse();

            Certificate certificate = new Certificate();
            certificate.setCourse(course);
            certificate.setStudent(student);
            certificate.setCreatedAt(new Date());
            String rndValue = String.valueOf(Math.random() * 89999 + 10000).substring(0, 5); //length 5
            certificate.setCertificateId("PDP-CER-" + rndValue);
            Certificate save = certificateRepository.save(certificate);

            return ApiResponse.builder()
                    .message("Certificate Created!")
                    .success(true)
                    .data(toResCertificateDTO(save))
                    .build();
        } else {
            return
                    new ApiResponse<>("Student with " + student_id + " id has already Certificated!", false);
        }
    }

    @Override
    public ApiResponse<?> getAllCertificate() {
        List<Certificate> certificateList = certificateRepository.findAll();
        return ApiResponse.builder()
                .message("Certificates List!")
                .success(true)
                .data(toResCertificateDTOList(certificateList))
                .build();
    }

    @Override
    public ApiResponse<?> getOne(Long id) {
        final var byId = certificateRepository.findById(id);
        return ApiResponse.builder()
                .message("Certificate by " + id + " id")
                .success(true)
                .data(toResCertificateDTO(byId.get()))
                .build();
    }

    @Override
    public ApiResponse<?> getCertificateById(String certificateId) {
        final var byId = certificateRepository.findByCertificateId(certificateId);
        return ApiResponse.builder()
                .message("Certificate by " + certificateId + " id")
                .success(true)
                .data(toResCertificateDTO(byId.get()))
                .build();
    }

    @Override
    public ApiResponse<?> deleteCertificate(Long id) {
        final var byId = certificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate", "id", id));
        certificateRepository.delete(byId);

        return new ApiResponse<>("Certificate Deleted!", true);
    }

    public ResCertificateDTO toResCertificateDTO(Certificate certificate) {
        return ResCertificateDTO.builder()
                .certificateId(certificate.getCertificateId())
                .courseName(certificate.getCourse().getName())
                .studentFullName(certificate.getStudent().getFullName())
                .createdAt(certificate.getCreatedAt().toString())
                .build();
    }

    public List<ResCertificateDTO> toResCertificateDTOList(List<Certificate> certificates) {
        return certificates.stream().map(this::toResCertificateDTO).toList();
    }
}