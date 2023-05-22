package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.DeleteRequestDTO;
import com.example.pdp_esm.dto.PaymentDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResDeletePaymentDTO;
import com.example.pdp_esm.dto.result.ResPaymentDTO;
import com.example.pdp_esm.dto.result.ResPaymentStudentInfo;
import com.example.pdp_esm.entity.Group;
import com.example.pdp_esm.entity.Payment;
import com.example.pdp_esm.entity.Student;
import com.example.pdp_esm.entity.enums.PayStatus;
import com.example.pdp_esm.entity.enums.PayType;
import com.example.pdp_esm.entity.requests.DeleteRequest;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.deleteRequests.DeletePaymentRequestsRepository;
import com.example.pdp_esm.repository.PaymentRepository;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.pdp_esm.entity.enums.PayStatus.RECEIVED;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final DeletePaymentRequestsRepository deletePaymentRequestsRepository;
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
        payment.setPayStatus(RECEIVED);
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

    public ApiResponse<?> createDeletePaymentRequest(DeleteRequestDTO deleteRequestDTO) {

        Optional<Payment> optionalPayment = Optional.ofNullable(paymentRepository.findById(deleteRequestDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", deleteRequestDTO.getId())));
        Payment payment = optionalPayment.get();

        DeleteRequest<Payment> deletePaymentRequest = new DeleteRequest<>();
        deletePaymentRequest.setObject((payment));

        if (deleteRequestDTO.getDescription().isEmpty())
            return new ApiResponse<>("Description is empty!", false);

        deletePaymentRequest.setDescription(deleteRequestDTO.getDescription());
        deletePaymentRequest.setCreatedAt(new Date());
        deletePaymentRequest.setActive(true);

        DeleteRequest<Payment> save =
                deletePaymentRequestsRepository.save(deletePaymentRequest);
        return
                new ApiResponse<>("Delete Payment Request created!", true, toResDeletePaymentDTO(save));
    }

    public ApiResponse<?> getAllDeletePaymentRequests() {
        List<DeleteRequest<Payment>> deletePaymentRequestList = deletePaymentRequestsRepository.findAll();

        return ApiResponse.builder()
                .message("Delete Payment Requests LIst ")
                .success(true)
                .data(toResDeletePaymentDTOList(deletePaymentRequestList))
                .build();
    }

    @Override
    public ApiResponse<?> deletePayment(Long payment_id) {

        Optional<Payment> optionalPayment = Optional.ofNullable(paymentRepository.findById(payment_id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", payment_id)));
        Payment payment = optionalPayment.get();

        payment.setPayStatus(PayStatus.CANCELLED);
        paymentRepository.save(payment);

        Optional<DeleteRequest<Payment>> optional = deletePaymentRequestsRepository.findById(payment_id);
        optional.get().setActive(false);

        deletePaymentRequestsRepository.save(optional.get());
        return
                new ApiResponse<>("Payment Deleted! ", true);
    }

    @Override
    public ApiResponse<?> getAllPaymentsByStatus(String status) {

        List<Payment> allPaymentsByStatus = paymentRepository.findAllByPayStatus(PayStatus.valueOf(status));

        return ApiResponse.builder()
                .message("All Payments List by " + status + " status")
                .success(true)
                .data(toResDTOList(allPaymentsByStatus))
                .build();
    }

    public ResPaymentDTO toResDTO(Payment payment) {

        Student student = payment.getStudent();
        Group group = student.getGroup();

        ResPaymentDTO resPaymentDTO = new ResPaymentDTO();
        resPaymentDTO.setAmount(payment.getAmount());
        resPaymentDTO.setPayType(String.valueOf(payment.getPayType()));
        resPaymentDTO.setPayStatus(payment.getPayStatus().toString());
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

    public ResDeletePaymentDTO toResDeletePaymentDTO(DeleteRequest<Payment> deletePaymentRequest) {
        return ResDeletePaymentDTO.builder()
                .description(deletePaymentRequest.getDescription())
                .requestCreated_time(String.valueOf(deletePaymentRequest.getCreatedAt()))
                .active(deletePaymentRequest.getActive())
                .resPaymentDTO(toResDTO(deletePaymentRequest.getObject()))
                .build();
    }

    public List<ResDeletePaymentDTO> toResDeletePaymentDTOList(List<DeleteRequest<Payment>> deletePaymentRequestList) {
        return deletePaymentRequestList.stream().map(this::toResDeletePaymentDTO).toList();
    }
}
