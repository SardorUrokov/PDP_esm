package com.example.pdp_esm.component;

import com.example.pdp_esm.auth.AuthenticationService;
import com.example.pdp_esm.auth.RegisterRequest;
import com.example.pdp_esm.entity.*;
import com.example.pdp_esm.entity.enums.*;
import com.example.pdp_esm.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.example.pdp_esm.entity.enums.Roles.*;

@Component
@RequiredArgsConstructor
public class Dataloader implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;
    private final QuestionRepository questionRepository;
    private final PositionRepository positionRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @Value("${spring.sql.init.mode}")
    private String mode;
    @Override
    public void run(String... args) {
        if (mode.equals("always")) {

            Course java_backend_course = courseRepository.save(new Course("Java Backend Development", 120000, CourseType.OFFLINE, true));
            Course android_course = courseRepository.save(new Course("Android Development", 110000, CourseType.HYBRID, true));
            Course frontEnd_course = courseRepository.save(new Course("Front-End Development", 100000, CourseType.ONLINE, true));

            Position mentor = positionRepository.save(new Position("Mentor"));
            Position mentor_assistant = positionRepository.save(new Position("Mentor Assistant"));

            Teacher java_teacher = teacherRepository.save(new Teacher("java_teacher Teacherov", "998914525468", "13@gmail.com", passwordEncoder.encode("password123"), "Male", USER, true, mentor, List.of(java_backend_course)));
            Teacher android_teacher = teacherRepository.save(new Teacher("android_teacher Teacherov", "998914525468", "17@gmail.com", passwordEncoder.encode("password123"), "Male", USER, true, mentor, List.of(android_course, java_backend_course)));
            Teacher front_end_teacher = teacherRepository.save(new Teacher("front_end_teacher Teacherov", "998914525468", "71@gmail.com", passwordEncoder.encode("password123"), "Male", USER, true, mentor, List.of(frontEnd_course)));
            Teacher teacher_assistant = teacherRepository.save(new Teacher("teacher_assistant Teacherov", "998914525468", "19@gmail.com", passwordEncoder.encode("password123"), "Female", USER, true, mentor_assistant, List.of(java_backend_course)));

            Group J1 = groupRepository.save(new Group("J1", java_backend_course, true, List.of(java_teacher, teacher_assistant), LocalDate.parse("2023-05-01")));
            Group A1 = groupRepository.save(new Group("A1", android_course, true, List.of(android_teacher, teacher_assistant), LocalDate.parse("2023-05-22")));
            Group F1 = groupRepository.save(new Group("F1", frontEnd_course, true, List.of(front_end_teacher), LocalDate.parse("2023-03-30")));

            Student j1_student = studentRepository.save(new Student("Sardor Urokov", "998914525468", "12@gmail.com", passwordEncoder.encode("password123"), "Male", USER, true, 1200000d, Status.STUDYING, J1));
            Student a1_student = studentRepository.save(new Student("Usmon Saidiy", "998914525468", "11@gmail.com", passwordEncoder.encode("password123"), "Male", USER, true, 1100000d, Status.WAITING, A1));
            Student a1_student1 = studentRepository.save(new Student("Anvar Anvarov", "998914525468", "21@gmail.com", passwordEncoder.encode("password123"), "Male", USER, true, 1100000d, Status.WAITING, A1));
            Student f1_student = studentRepository.save(new Student("MuhammadAziz Zayniddinov", "998914525468", "14@gmail.com", passwordEncoder.encode("password123"), "Male", USER, true, 1000000d, Status.COMPLETED, F1));

            Question question1 = questionRepository.save(new Question(android_course, "There's a 1st question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer", true));
            Question question2 = questionRepository.save(new Question(android_course, "There's a 2nd question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer", true));
            Question question3 = questionRepository.save(new Question(java_backend_course, "There's a 3rd question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer", true));
            Question question4 = questionRepository.save(new Question(java_backend_course, "There's a 4th question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer", true));
            Question question5 = questionRepository.save(new Question(frontEnd_course, "There's a 5th question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer", true));
            Question question6 = questionRepository.save(new Question(frontEnd_course, "There's a 6th question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer", true));

            Payment payment1 = paymentRepository.save(new Payment(1100000d, a1_student, PayType.CASH, PayStatus.RECEIVED));
            Payment payment2 = paymentRepository.save(new Payment(1000000d, f1_student, PayType.BY_PAYMENT_APP, PayStatus.RECEIVED));
            Payment payment3 = paymentRepository.save(new Payment(1200000d, j1_student, PayType.BY_CARD, PayStatus.RECEIVED));

            var aaa = RegisterRequest.builder()
                    .fullName("AAA")
                    .email("a@gmail.com")
                    .password("password")
                    .role(USER)
                    .build();
//            System.out.println("AAA token: " + authenticationService.register(aaa).getAccessToken());
        }
    }
}