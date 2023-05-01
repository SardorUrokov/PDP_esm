package com.example.pdp_esm.component;

import com.example.pdp_esm.entity.*;
import com.example.pdp_esm.entity.enums.PayType;
import com.example.pdp_esm.entity.enums.ResultType;
import com.example.pdp_esm.entity.enums.Status;
import com.example.pdp_esm.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Dataloader implements CommandLineRunner {

    private final PositionRepository positionRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;
    private final QuestionRepository questionRepository;
    private final ExamResultRepository resultRepository;

    @Value("${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) {
        if (mode.equals("always")) {

            Course java_backend_course = courseRepository.save(new Course("Java Backend Development", 120000));
            Course android_course = courseRepository.save(new Course("Android Development", 120000));
            Course frontEnd_course = courseRepository.save(new Course("Front-End Development", 100000));

            Position mentor = positionRepository.save(new Position("Mentor"));
            Position mentor_assistant = positionRepository.save(new Position("Mentor Assistant"));

            Teacher teacher = teacherRepository.save(new Teacher("Teacher Teacherov", "1234567", true, mentor, List.of(java_backend_course)));
            Teacher teacher1 = teacherRepository.save(new Teacher("Teacher Teacherov", "1234567", true, mentor, List.of(android_course, java_backend_course)));
            Teacher teacher2 = teacherRepository.save(new Teacher("Teacher Teacherov", "1234567", true, mentor, List.of(frontEnd_course)));

            Group J1 = groupRepository.save(new Group("J1", java_backend_course, teacher));
            Group A1 = groupRepository.save(new Group("A1", android_course, teacher1));
            Group F1 = groupRepository.save(new Group("F1", java_backend_course, teacher2));

            Student j1_student = studentRepository.save(new Student("Sardor Urokov", "998914525468", 1200000d, true, Status.STUDYING, J1));
            Student a1_student = studentRepository.save(new Student("Usmon Saidiy", "111111111", 1200000d, true, Status.STUDYING, A1));
            Student a2_student = studentRepository.save(new Student("Anvar Anvarov", "7777777", 1200000d, true, Status.STUDYING, A1));
            Student f1_student = studentRepository.save(new Student("MuhammadAziz Zayniddinov", "998998998999", 1000000d, true, Status.STUDYING, F1));

            Payment payment1 = paymentRepository.save(new Payment(1200000d, a1_student, PayType.CASH));
            Payment payment2 = paymentRepository.save(new Payment(1000000d, f1_student, PayType.BY_PAYMENT_APP));
            Payment payment3 = paymentRepository.save(new Payment(1200000d, j1_student, PayType.BY_CARD));

            Question question1 = questionRepository.save(new Question( android_course, "There's a question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer"));
            Question question2 = questionRepository.save(new Question( android_course, "There's a question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer"));
            Question question3 = questionRepository.save(new Question( java_backend_course, "There's a question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer"));
            Question question4 = questionRepository.save(new Question( java_backend_course, "There's a question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer"));
            Question question5 = questionRepository.save(new Question( frontEnd_course, "There's a question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer"));
            Question question6 = questionRepository.save(new Question( frontEnd_course, "There's a question", "There's a true answer", "There's a 1st wrong answer", "There's a 2nd wrong answer", "There's a 3rd wrong answer"));

            ExamResult examResult1 = resultRepository.save(new ExamResult(81.8f, j1_student, ResultType.SUCCESS, List.of(question3, question4)));
            ExamResult examResult2 = resultRepository.save(new ExamResult(57f, a1_student, ResultType.FAIL, List.of(question1, question2)));
            ExamResult examResult3 = resultRepository.save(new ExamResult(67f, a2_student, ResultType.SUCCESS, List.of(question2, question4)));
            ExamResult examResult4 = resultRepository.save(new ExamResult(78.3f, f1_student, ResultType.SUCCESS, List.of(question5, question6)));
        }
    }
}
