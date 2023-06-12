package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.CalculationInterval;
import com.example.pdp_esm.dto.DeleteRequestDTO;
import com.example.pdp_esm.dto.PaymentDTO;
import com.example.pdp_esm.dto.SumOfPaymentsDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResDeletePaymentDTO;
import com.example.pdp_esm.dto.result.ResPaymentDTO;
import com.example.pdp_esm.dto.result.ResPaymentStudentInfo;
import com.example.pdp_esm.entity.Course;
import com.example.pdp_esm.entity.Group;
import com.example.pdp_esm.entity.Payment;
import com.example.pdp_esm.entity.Student;
import com.example.pdp_esm.entity.enums.PayStatus;
import com.example.pdp_esm.entity.enums.PayType;
import com.example.pdp_esm.entity.requests.DeletePaymentRequest;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.CourseRepository;
import com.example.pdp_esm.repository.deleteRequests.DeletePaymentRequestsRepository;
import com.example.pdp_esm.repository.PaymentRepository;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.pdp_esm.entity.enums.PayStatus.RECEIVED;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final DeletePaymentRequestsRepository deletePaymentRequestsRepository;
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EmailService emailService;

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

        student.setBalance(student.getBalance() + payment.getAmount());
        studentRepository.save(student);

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

        Optional<Payment> optionalPayment = Optional.ofNullable(paymentRepository.findById(deleteRequestDTO.getData_id())
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", deleteRequestDTO.getData_id())));
        Payment payment = optionalPayment.get();

        DeletePaymentRequest deletePaymentRequest = new DeletePaymentRequest();
        deletePaymentRequest.setPayment((payment));

        if (deleteRequestDTO.getDescription().isEmpty())
            return new ApiResponse<>("Description is empty!", false);

        deletePaymentRequest.setDescription(deleteRequestDTO.getDescription());
        deletePaymentRequest.setCreatedAt(new Date());
        deletePaymentRequest.setActive(true);

        DeletePaymentRequest save =
                deletePaymentRequestsRepository.save(deletePaymentRequest);
        return
                new ApiResponse<>("Delete Payment Request created!", true, toResDeletePaymentDTO(save));
    }

    public ApiResponse<?> getAllDeletePaymentRequests() {
        List<DeletePaymentRequest> deletePaymentRequestList = deletePaymentRequestsRepository.findAll();

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

        Optional<DeletePaymentRequest> optionalRequest = Optional.ofNullable(deletePaymentRequestsRepository.findByPaymentId(payment_id)
                .orElseThrow(() -> new ResourceNotFoundException("Delete Payment Request", "id", payment_id)));
        final var deletePaymentRequest = optionalRequest.get();

        final var byId = studentRepository.findById(payment.getStudent().getId());
        final var student = byId.get();
        final var currentBalance = student.getBalance();
        ApiResponse response = new ApiResponse();

        if (currentBalance >= payment.getAmount()) {

            student.setBalance(student.getBalance() - payment.getAmount());
            studentRepository.save(student);

            if (currentBalance - student.getBalance() == payment.getAmount()) {

                payment.setPayStatus(PayStatus.CANCELLED);
                paymentRepository.save(payment);

                deletePaymentRequest.setActive(false);
                deletePaymentRequestsRepository.save(deletePaymentRequest);

                response.setMessage("Payment Deleted! ");
                response.setSuccess(true);
            }
        } else {
            response.setMessage("Something went wrong! ");
            response.setSuccess(false);
        }
        return response;
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

    @Override
    public ApiResponse<?> calculateSumOfPayments(CalculationInterval interval) throws ParseException {

        final String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date parseFrom = sdf.parse(interval.getFrom());
        Date parseTo = sdf.parse(interval.getTo());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseTo);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date updatedTo = calendar.getTime();

        List<Payment> payments = paymentRepository.findPaymentsWithinInterval(parseFrom, updatedTo);
        double sum = 0.0;
        List<ResPaymentDTO> resPaymentDTOList = new ArrayList<>();

        for (Payment payment : payments) {
            sum += payment.getAmount();
            resPaymentDTOList.add(toResDTO(payment));
        }

        return ApiResponse.builder()
                .message("Sum of Payments from " + interval.getFrom() + " to " + interval.getTo())
                .success(true)
                .data(SumOfPaymentsDTO.builder()
                        .sumOfIntervalPayments(sum)
                        .resPaymentDTOList(resPaymentDTOList)
                        .interval(interval)
                        .build())
                .build();
    }

    public void checkStudentBalance (Long student_id) {
        final var studentBalance = paymentRepository.getBalance(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student's Balance", "student_id", student_id));

        final var courseId = studentRepository.findById(student_id).get().getGroup().getCourse().getId();
        final var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "courseId", courseId));
        final var coursePrice = course.getPrice();

        if (studentBalance < coursePrice){
            Double lacking = (coursePrice - studentBalance);
            emailService.sendInsufficientFundsNotification(student_id, lacking);
        }
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

    public ResDeletePaymentDTO toResDeletePaymentDTO(DeletePaymentRequest deletePaymentRequest) {
        return ResDeletePaymentDTO.builder()
                .description(deletePaymentRequest.getDescription())
                .requestCreated_time(String.valueOf(deletePaymentRequest.getCreatedAt()))
                .active(deletePaymentRequest.getActive())
                .resPaymentDTO(toResDTO(deletePaymentRequest.getPayment()))
                .build();
    }

    public List<ResDeletePaymentDTO> toResDeletePaymentDTOList(List<DeletePaymentRequest> deletePaymentRequestList) {
        return deletePaymentRequestList.stream().map(this::toResDeletePaymentDTO).toList();
    }
}