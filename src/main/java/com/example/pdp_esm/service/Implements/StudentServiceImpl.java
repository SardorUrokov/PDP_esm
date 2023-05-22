package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.result.ResStudentDTO;
import com.example.pdp_esm.dto.StudentDTO;
import com.example.pdp_esm.entity.*;
import com.example.pdp_esm.entity.enums.Roles;
import com.example.pdp_esm.entity.enums.Status;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.GroupRepository;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.repository.UserRepository;
import com.example.pdp_esm.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.pdp_esm.entity.enums.Roles.USER;
import static com.example.pdp_esm.entity.enums.Status.*;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public ApiResponse<?> createStudent(StudentDTO studentDTO) {

        Optional<Group> optionalGroup = Optional.ofNullable(groupRepository.findById(studentDTO.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", studentDTO.getGroupId())));
        Group group = optionalGroup.get();

        final boolean existedByEmail = userRepository.existsByEmail(studentDTO.getEmail());
        boolean exists = studentRepository.existsByPhoneNumberAndGroup(studentDTO.getPhoneNumber(), group);

        if (exists) {
            return ApiResponse.builder()
                    .message("Such a Student is already saved!")
                    .success(false)
                    .build();
        } else if (existedByEmail) {
            return ApiResponse.builder()
                    .message("User with this email is already created!")
                    .data(studentDTO.getEmail())
                    .success(false)
                    .build();
        } else {
            Student student = new Student();
            Student save = settingValues(student, studentDTO);

            return ApiResponse.builder()
                    .message("Student Saved!")
                    .success(true)
                    .data(toResStudentDTO(Collections.singletonList(save)))
                    .build();
        }
    }

    @Override
    public ApiResponse<?> getAllStudents() {

        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) return ApiResponse.builder()
                .message("Students not found")
                .success(false)
                .build();

        else return ApiResponse.builder()
                .message("Students List")
                .success(true)
                .data(toResStudentDTO(students))
                .build();
    }

    @Override
    public ApiResponse<?> getOneStudent(Long student_id) {
        Optional<Student> optionalStudent = Optional.ofNullable(studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", student_id)));
        Student student = optionalStudent.get();

        return ApiResponse.builder()
                .message("Student with " + student_id + " id")
                .success(true)
                .data(toResStudentDTO(Collections.singletonList(student)))
                .build();
    }

    @Override
    public ApiResponse<?> updateStudent(Long student_id, StudentDTO studentDTO) {

        Optional<Student> optionalStudent = Optional.ofNullable(studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", student_id)));
        Student student = optionalStudent.get();
        Student save = settingValues(student, studentDTO);
        return ApiResponse.builder()
                .message("Student Updated!")
                .success(true)
                .data(toResStudentDTO(Collections.singletonList(save)))
                .build();
    }

    @Override
    public ApiResponse<?> deleteStudent(Long student_id) {
        boolean exists = studentRepository.existsByIdAndActiveTrue(student_id);

        if (!exists) return ApiResponse.builder()
                .message("Student not found!")
                .success(false)
                .build();
        else {
            Optional<Student> optionalStudent = studentRepository.findById(student_id);
            Student student = optionalStudent.get();
            student.setActive(false);
            studentRepository.save(student);

            return ApiResponse.builder()
                    .message("Student Removed!")
                    .success(true)
                    .build();
        }
    }

    @Override
    public ApiResponse<?> getAllActiveFalseStudents() {
        List<Student> studentList = studentRepository.findAllByActiveFalse();

        if (studentList.isEmpty()) return ApiResponse.builder()
                .message("Removed Students not found")
                .success(true)
                .build();

        else return ApiResponse.builder()
                .message("Removed Students List")
                .success(true)
                .data(toResStudentDTO(studentList))
                .build();
    }

    @Override
    public ApiResponse<?> getOneActiveFalseStudent(Long student_id) {
        Optional<Student> optionalStudent = Optional.ofNullable(studentRepository.findByIdAndActiveFalse(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Removed Student", "id", student_id)));
        Student student = optionalStudent.get();

        return ApiResponse.builder()
                .message("Removed Student with " + student_id + " id")
                .success(true)
                .data(toResStudentDTO(Collections.singletonList(student)))
                .build();
    }

    @Override
    public ApiResponse<?> getCompletedStudents() {

        List<Student> completedStudents = new ArrayList<>();
        LocalDate minusDays = LocalDate.now().minusDays(60);
        List<Student> groupStartDateBeforeStudents = studentRepository.findAllByGroupStartDateBeforeAndStatusNotAndStatusNot(minusDays, COMPLETED, SUSPENDED);

        for (Student student : groupStartDateBeforeStudents) {
            student.setStatus(COMPLETED);
            studentRepository.save(student);
            completedStudents.add(student);
        }

        return ApiResponse.builder()
                .message("Completed Students")
                .success(true)
                .data(toResStudentDTO(completedStudents))
                .build();
    }

    public ApiResponse<?> getAllStudentsByStatus(Status status) {

        List<Student> allByStatus = studentRepository.findAllByStatus(status);
        return ApiResponse.builder()
                .message(status + " Students List")
                .success(true)
                .data(toResStudentDTO(allByStatus))
                .build();

    }

    public List<ResStudentDTO> toResStudentDTO(List<Student> students) {
        return students.stream()
                .map(student -> ResStudentDTO.builder()
                        .fullName(student.getFullName())
                        .phoneNumber(student.getPhoneNumber())
                        .email(student.getEmail())
                        .balance(student.getBalance())
                        .status(String.valueOf(student.getStatus()))
                        .gender(student.getGender()).build()
                ).toList();
    }

    public ResStudentDTO resStudentDTO(Student student){
        return ResStudentDTO.builder()
                .fullName(student.getFullName())
                .phoneNumber(student.getPhoneNumber())
                .email(student.getEmail())
                .status(String.valueOf(student.getStatus()))
                .gender(student.getGender())
                .balance(student.getBalance())
                .build();
    }

    public Student settingValues(Student student, StudentDTO studentDTO) {
        Optional<Group> optionalGroup = Optional.ofNullable(groupRepository.findById(studentDTO.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", studentDTO.getGroupId())));
        Group group = optionalGroup.get();
        LocalDate now = LocalDate.now();

        student.setFullName(studentDTO.getFullName());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());
        student.setGroup(group);
        student.setGender(studentDTO.getGender());
        student.setBalance(studentDTO.getBalance());
        student.setRoles(USER);
        student.setActive(true);
        if ((group.getStartDate().isBefore(now))) student.setStatus(STUDYING);
        else if ((group.getStartDate().isAfter(now))) student.setStatus(WAITING);
        else student.setStatus(COMPLETED);

        return studentRepository.save(student);
    }
}