package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResCertificateDTO;
import com.example.pdp_esm.entity.Certificate;
import com.example.pdp_esm.entity.ExamResult;
import com.example.pdp_esm.entity.Student;
import com.example.pdp_esm.entity.Teacher;
import com.example.pdp_esm.entity.enums.ResultType;
import com.example.pdp_esm.entity.enums.Status;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.CertificateRepository;
import com.example.pdp_esm.repository.ExamResultRepository;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final StudentRepository studentRepository;
    private final FileStorageService fileStorageService;
    private final ExamResultRepository examResultRepository;

    @Override
    public ApiResponse<?> createCertificate(Long student_id) {

        final var student = studentRepository.findById(student_id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student", "id", student_id));
        final var byId = certificateRepository.findByStudentId(student_id);

        if (byId.isEmpty()) {
            final var course = student.getGroup().getCourse();

            Certificate certificate = new Certificate();
            certificate.setCourse(course);
            certificate.setStudent(student);
            certificate.setCreatedAt(new Date());
            String rndValue = String.valueOf(Math.random() * 89999 + 10000).substring(0, 5); //length 5
            certificate.setCertificateNumber("PDP-CER-" + rndValue);
            Certificate save = certificateRepository.save(certificate);

            final var generatedCertificate = generateCertificate(toResCertificateDTO(save));
            try {
                assert generatedCertificate != null;
                fileStorageService.uploadFile(generatedCertificate);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return ApiResponse.builder()
                    .message("Certificate Created!")
                    .success(true)
                    .data(toResCertificateDTO(save))
                    .build();
        } else {
            Certificate byStudentId = certificateRepository.findByStudentId(student_id).orElseThrow();
            return
                    new ApiResponse<>("Student with " + student_id + " id has already Certificated!",false, toResCertificateDTO(byStudentId));
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
    public ApiResponse<?> getCertificateById(String certificateNumber) {
        final var byId = certificateRepository.findByCertificateNumber(certificateNumber);
        return ApiResponse.builder()
                .message("Certificate by " + certificateNumber + " id")
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
                .certificateId(certificate.getCertificateNumber())
                .courseName(certificate.getCourse().getName())
                .studentFullName(certificate.getStudent().getFullName())
                .createdAt(certificate.getCreatedAt().toString())
                .build();
    }

    public List<ResCertificateDTO> toResCertificateDTOList(List<Certificate> certificates) {
        return certificates.stream().map(this::toResCertificateDTO).toList();
    }

    public static MultipartFile generateCertificate(ResCertificateDTO certificateDTO) {

        String folder = "C:\\Users\\user\\Desktop\\PDP_Certificates\\";
        String contentType = "application/pdf";
        String fileName = certificateDTO.getCertificateId();

        final var createdAt = certificateDTO.getCreatedAt();
        String dayOfMonth = createdAt.substring(3, 10);
        String year = createdAt.substring(23);
        String date = dayOfMonth + year;

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                // Set background color for the list
//                contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
//                contentStream.fillRect(0, 0, 800, 900);

                // Set font color
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                // Add frame around the list
                contentStream.addRect(20, 20, 555, 800);
                contentStream.stroke();

                // Add text
                contentStream.beginText();
                contentStream.newLineAtOffset(85, 700);
                contentStream.setFont(PDType1Font.COURIER_BOLD_OBLIQUE, 28);
                contentStream.showText("Certificate of Completion");
                contentStream.newLineAtOffset(55, -80);
                contentStream.setFont(PDType1Font.COURIER_OBLIQUE, 20);
                contentStream.showText("This is to certify that");
                contentStream.newLineAtOffset(-65, -50);
                contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 30);
                contentStream.showText(certificateDTO.getStudentFullName());
                contentStream.setFont(PDType1Font.COURIER_OBLIQUE, 15);
                contentStream.showText(" has successfully completed ");
                contentStream.newLineAtOffset(1, -70);
                contentStream.setFont(PDType1Font.COURIER_OBLIQUE, 12);
                contentStream.showText("the course  ");
                contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 30);
                contentStream.showText(certificateDTO.getCourseName());
                contentStream.newLineAtOffset(-10, -400);
                contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 15);
                contentStream.showText("CEO  B.D.Ikramov:  __________________");
                contentStream.newLineAtOffset(-10, -60);
                contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 12);
                contentStream.showText(date);
                contentStream.newLineAtOffset(385, -1);
                contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 12);
                contentStream.showText("ID: " + certificateDTO.getCertificateId());
                contentStream.endText();
            }
            document.save(folder + certificateDTO.getCertificateId());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            final var byteArray = baos.toByteArray();
            return new MockMultipartFile(fileName, fileName, contentType, byteArray);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ResCertificateDTO> automaticGenerateCertificates(){

        final var allByStatus = studentRepository.findAllByStatus(Status.COMPLETED);
        List<Long> completedStudentsIds = new ArrayList<>();

        for (Student byStatus : allByStatus) {
            final var examResult = examResultRepository.findByStudentId(byStatus.getId());
            if (examResult.get().getResultType().equals(ResultType.SUCCESS)) {
                completedStudentsIds.add(byStatus.getId());
            }
        }

        if (!completedStudentsIds.isEmpty()){
            for (Long completedStudentsId : completedStudentsIds) {
                createCertificate(completedStudentsId);
            }
        }
        List<Certificate> certificates =
                completedStudentsIds.stream().map(certificateRepository::getById).toList();

        return toResCertificateDTOList(certificates);
    }
}