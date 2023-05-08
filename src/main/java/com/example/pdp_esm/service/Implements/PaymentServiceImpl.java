package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.PaymentDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResPaymentDTO;
import com.example.pdp_esm.dto.result.ResPaymentStudentInfo;
import com.example.pdp_esm.entity.Group;
import com.example.pdp_esm.entity.Payment;
import com.example.pdp_esm.entity.Student;
import com.example.pdp_esm.entity.enums.PayType;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.PaymentRepository;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;

    @Override
    public ApiResponse<?> createPayment(PaymentDTO paymentDTO) {

        Long studentId = paymentDTO.getStudentId();
        Optional<Student> optionalStudent = Optional.ofNullable(studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId)));
        Student student = optionalStudent.get();

        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setStudent(student);
        payment.setPayType(PayType.valueOf(paymentDTO.getPayType()));
        Payment save = paymentRepository.save(payment);

        return ApiResponse.builder()
                .message("Payment Saved!")
                .success(true)
                .data(toResDTO(save))
                .build();
    }

    @Override
    public ApiResponse<?> getAllPayments() {

        List<Payment> paymentList = paymentRepository.findAll();

        return ApiResponse.builder()
                .message("Payment List")
                .success(true)
                .data(toResDTOList(paymentList))
                .build();
    }

    @Override
    public ApiResponse<?> getOnePayment(Long payment_id) {
        Optional<Payment> optionalPayment = Optional.ofNullable(paymentRepository.findById(payment_id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", payment_id)));
        Payment payment = optionalPayment.get();

        return ApiResponse.builder()
                .message("Payment Saved!")
                .success(true)
                .data(toResDTO(payment))
                .build();
    }


//    Security qo'shilganda yoziladi. Admin tomonidan Payment delete qilish uchun Manager ga request jo'natiladi
//    Manager ko'rib tasdiqlaguniga qadar pending bo'b turadi. Manager tasdiqlasa avtomatik delete bo'ladi
    @Override
    public ApiResponse<?> deletePayment(Long payment_id) {
        return new ApiResponse<>("DELETE PAYMENT METHOD NOT CREATED", false);
    }

    public ResPaymentDTO toResDTO(Payment payment) {

        Student student = payment.getStudent();
        Group group = student.getGroup();

        ResPaymentDTO resPaymentDTO = new ResPaymentDTO();
        resPaymentDTO.setAmount(payment.getAmount());
        resPaymentDTO.setPayType(String.valueOf(payment.getPayType()));
        resPaymentDTO.setDate(String.valueOf(payment.getCreatedAt()));
        resPaymentDTO.setStudentInfo(ResPaymentStudentInfo.builder()
                .studentName(student.getFullName())
                .studentGroupName(group.getGroupName())
                .studentPhoneNumber(student.getPhoneNumber())
                .build());
        return resPaymentDTO;
    }

    public List<ResPaymentDTO> toResDTOList(List<Payment> payments) {
        return payments.stream().map(this::toResDTO).toList();
    }
}