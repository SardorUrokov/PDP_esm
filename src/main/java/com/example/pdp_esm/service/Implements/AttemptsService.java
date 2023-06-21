package com.example.pdp_esm.service.Implements;

import java.util.List;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import com.example.pdp_esm.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.repository.ModulesRepository;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.repository.AttemptsRepository;
import com.example.pdp_esm.exception.ResourceNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttemptsService {

    private final AttemptsRepository attemptsRepository;
    private final StudentRepository studentRepository;
    private final ModulesRepository modulesRepository;

    public boolean checkStudentAttempts(Long student_id, Long module_id) {

        final var attempts = attemptsRepository.findByStudent_IdAndModules_Id(student_id, module_id)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt", "student_id", student_id));

        final var temp = attempts.getAttempts();
        return temp != 0;
    }

    public void updateAttempts(Long student_id, Long module_id) {

        final Attempts attempt = attemptsRepository
                .findByStudent_IdAndModules_Id(student_id, module_id)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt", "student_id", student_id));

        Integer temp = attempt.getAttempts();

        if (temp != null && temp != 0)
            temp -= 1;

        attempt.setAttempts(temp);
        attemptsRepository.save(attempt);
//        ApiResponse.builder()
//                .message("Student Attempts Updated!")
//                .success(true)
//                .data(attempt)
//                .build();
    }

    public void setAttempts(List<Student> students, int attempt) {
        for (Student student : students) {

            Attempts attempts = new Attempts();
            final var studentGroup = student.getGroup();

            final List<Modules> modulesList = modulesRepository.
                    findByCourseIdAndGroupIdOrderByOrdinalNumberDesc(
                            studentGroup.getCourse().getId(),
                            studentGroup.getId()
                    );

            final var module = modulesList.get(0);

            attempts.setAttempts(attempt);
            attempts.setStudent(student);
            attempts.setModules(module);
            final var saved = attemptsRepository.save(attempts);
            log.warn("Attempts saved to Student -> {}", saved.getStudent());
        }
    }

    public void setAttemptsWithModule(Modules module) {

        List<Student> checkedStudents = new ArrayList<>();
        for (GroupModule groupModule : module.getGroupModules()) {

            final var studentList = studentRepository.findAllByGroupId(groupModule.getGroup().getId());

            for (Student student : studentList) {
                final var exists = attemptsRepository.existsByStudentIdAndModules_Id(student.getId(), module.getId());

                if (!exists) {
                    checkedStudents.add(student);
                }
            }
            setAttempts(checkedStudents, 3);
        }
    }
}