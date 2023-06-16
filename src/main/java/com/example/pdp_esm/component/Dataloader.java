package com.example.pdp_esm.component;

import com.example.pdp_esm.auth.AuthenticationService;
import com.example.pdp_esm.auth.RegisterRequest;
import com.example.pdp_esm.entity.*;
import com.example.pdp_esm.entity.enums.*;
import com.example.pdp_esm.entity.test.Answer;
import com.example.pdp_esm.repository.*;
import com.example.pdp_esm.repository.test.AnswerRepository;
import com.example.pdp_esm.service.Implements.CertificateDownloader;
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
    private final ExamResultRepository resultRepository;
    private final AuthenticationService authenticationService;
    private final AnswerRepository answerRepository;

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

            Student j1_student = studentRepository.save(new Student("Sardor Urokov", "998914525468", "urokovsardor04@gmail.com", passwordEncoder.encode("password123"), "Male", USER, true, 1200000d, Status.STUDYING, J1));
            Student a1_student = studentRepository.save(new Student("Usmon Saidiy", "998914525468", "urokovsardor15@gmail.com", passwordEncoder.encode("password123"), "Male", USER, true, 1100000d, Status.WAITING, A1));
            Student a1_student1 = studentRepository.save(new Student("Anvar Anvarov", "998914525468", "urokovsardorgpt@gmail.com", passwordEncoder.encode("password123"), "Male", USER, true, 1100000d, Status.WAITING, A1));
            Student f1_student = studentRepository.save(new Student("MuhammadAziz Zayniddinov", "998914525468", "sardorurokov92@gmail.com", passwordEncoder.encode("password123"), "Male", USER, true, 1000000d, Status.COMPLETED, F1));
            
            Question question1 = questionRepository.save(new Question("There's a 1st question", QuestionType.TRUE_FALSE, true, android_course));
            Question question2 = questionRepository.save(new Question("There's a 2nd question", QuestionType.TRUE_FALSE, true, android_course));
            Question question3 = questionRepository.save(new Question("There's a 3rd question", QuestionType.TEST, true, java_backend_course));
            Question question4 = questionRepository.save(new Question("There's a 4th question", QuestionType.TEST, true, java_backend_course));
            Question question5 = questionRepository.save(new Question("There's a 5th question", QuestionType.SEQUENCE, true, frontEnd_course));
            Question question6 = questionRepository.save(new Question("There's a 6th question", QuestionType.SEQUENCE, true, frontEnd_course));
            Question question7 = questionRepository.save(new Question("There's a 7th question", QuestionType.WRITE_MISSING_WORD, true, java_backend_course));

            Answer answerTest = answerRepository.save(new Answer("TRUE", true, 1, question1));
            Answer answerTest1 = answerRepository.save(new Answer("FALSE", false, 2, question1));
 
            Answer answerTest2 = answerRepository.save(new Answer("bbb", false, 1, question3));
            Answer answerTest3 = answerRepository.save(new Answer("aaa", true, 2, question3));
            Answer answerTest4 = answerRepository.save(new Answer("ddd", false, 3, question3));
            Answer answerTest5 = answerRepository.save(new Answer("ssss", false, 4, question3));

            Answer answerTest6 = answerRepository.save(new Answer("a1", true, 1, question5));
            Answer answerTest7 = answerRepository.save(new Answer("a2", true, 2, question5));
            Answer answerTest8 = answerRepository.save(new Answer("a3", true, 3, question5));

            Answer answerTest9 = answerRepository.save(new Answer("there's a missing word", true, 1, question7));

            ExamResult examResult1 = resultRepository.save(new ExamResult(80f, f1_student, ResultType.SUCCESS, List.of(question5, question6)));
            ExamResult examResult2 = resultRepository.save(new ExamResult(60f, a1_student1, ResultType.SUCCESS, List.of(question1, question2)));
            ExamResult examResult3 = resultRepository.save(new ExamResult(50f, a1_student, ResultType.FAIL, List.of(question2, question1)));
//            ExamResult examResult4 = resultRepository.save(new ExamResult(70f, j1_student, ResultType.SUCCESS, List.of(question3, question4)));

            Payment payment1 = paymentRepository.save(new Payment(1100000d, a1_student, PayType.CASH, PayStatus.RECEIVED));
            Payment payment2 = paymentRepository.save(new Payment(1000000d, f1_student, PayType.BY_PAYMENT_APP, PayStatus.RECEIVED));
            Payment payment3 = paymentRepository.save(new Payment(1200000d, j1_student, PayType.BY_CARD, PayStatus.RECEIVED));

            var aaa = RegisterRequest.builder()
                    .fullName("AAA")
                    .email("a@gmail.com")
                    .password("password")
                    .role(USER)
                    .build();
            System.out.println("AAA user token: " + authenticationService.register(aaa).getAccessToken());

        }
    }
}