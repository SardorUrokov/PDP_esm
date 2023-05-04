package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.ApiResponse;
import com.example.pdp_esm.dto.ResStudentDTO;
import com.example.pdp_esm.dto.StudentDTO;
import com.example.pdp_esm.entity.Student;
import com.example.pdp_esm.repository.StudentRepository;
import com.example.pdp_esm.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    @Override
    public ApiResponse<?> create(StudentDTO studentDTO) {
        return null;
    }

    @Override
    public ApiResponse<?> getAll() {

        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) return ApiResponse.builder()
                .message("There are no students listed")
                .success(false)
                .build();

        else return ApiResponse.builder()
                .message("Students List")
                .success(true)
                .data(toResStudentDTO(students))
                .build();
    }

    public List<ResStudentDTO> toResStudentDTO(List<Student> students) {
        return students.stream()
                .map(student -> ResStudentDTO.builder()
                        .fullName(student.getFullName())
                        .phoneNumber(student.getPhoneNumber())
                        .email(student.getEmail())
                        .balance(student.getBalance())
                        .gender(student.getGender()).build()
                ).toList();
    }
}