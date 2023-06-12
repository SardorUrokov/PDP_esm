package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.entity.User;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String sender;

    public String verifyCode(User user) {

        //6-xonali email tasdiqlash kodi
        String otpCode = String.valueOf(Math.random() * 899999 + 100000);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Verification Code : " + otpCode);
        simpleMailMessage.setText("Email Verifying");
        user.setVerifyCode(otpCode);

        javaMailSender.send(simpleMailMessage);
        userRepository.save(user);
        return otpCode;
    }

    public void sendInsufficientFundsNotification(Long student_id, Double lackingMoney) {

        final var student = studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "student_id", student_id));

        final var courseName = student.getGroup().getCourse().getName();
        final var studentEmail = student.getEmail();

        String text =
                "Good Day " + student.getFullName() + "!\n" +
                        "\nWe inform you that you don't have enough funds in your balance to proceed to the next module.\n" +
                        "Please top up your balance by " + lackingMoney + " uzs within 3 working days in order to proceed to the next module and continue the course " + courseName + ".\n" +
                        "\nBest regards,\n" +
                        "Team of PDP Finance Department.\n";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(studentEmail);
        simpleMailMessage.setSubject(text);
        simpleMailMessage.setText("Insufficient Funds");

        javaMailSender.send(simpleMailMessage);
    }

    public void sendCongratulationCompletedStudent(Long student_id, String certificate_id) {

        final var student = studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "student_id", student_id));

        final var courseName = student.getGroup().getCourse().getName();
        final var studentEmail = student.getEmail();

        String text =
                "Dear " + student.getFullName() + "!\n" +
                        "We are glad that you have trusted us, joined us, studied in our " + courseName + " and successfully completed this course!" +
                        "\nWe wish you great success and a promising career in the future.\n" +
                        "\nYou can download your certificate in PDF form by entering " + certificate_id + " through the following link.\n" +
                        "Link for Download: http://localhost:8989/api/download/ \n" +
                        "\nBest regards,\n" +
                        "Team of PDP IT Academy.\n";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(studentEmail);
        simpleMailMessage.setSubject(text);
        simpleMailMessage.setText("Congratulation");
    }
}