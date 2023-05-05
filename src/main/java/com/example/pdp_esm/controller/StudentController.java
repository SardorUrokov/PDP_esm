package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.StudentDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.enums.Status;
import com.example.pdp_esm.service.Implements.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;

    @PostMapping("/student/create")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO) {
        ApiResponse<?> response = studentService.createStudent(studentDTO);
        if (response.isSuccess()) log.warn("Student Created! -> {}", response.getData());
        else log.error(response.getMessage(), " -> {}" + response.getData());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/student")
    public ResponseEntity<?> getAllStudents(){

        ApiResponse<?> response = studentService.getAllStudents();
        if (response.isSuccess()) log.warn("Getting All Students! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getOneStudent(@PathVariable Long id) {

        ApiResponse<?> response = studentService.getOneStudent(id);
        if (response.isSuccess()) log.warn("Getting Student {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {

        ApiResponse<?> response = studentService.updateStudent(id, studentDTO);
        if (response.isSuccess()) log.warn("Student Updated {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {

        ApiResponse<?> response = studentService.deleteStudent(id);
        if (response.isSuccess()) log.warn("Student Removed {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/removedStudent")
    public ResponseEntity<?> getAllRemovedStudents(){

        ApiResponse<?> response = studentService.getAllActiveFalseStudents();
        if (response.isSuccess()) log.warn("Getting All Removed Students! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/removedStudent/{id}")
    public ResponseEntity<?> getOneRemovedStudent(@PathVariable Long id){

        ApiResponse<?> response = studentService.getOneActiveFalseStudent(id);
        if (response.isSuccess()) log.warn("Getting One Removed Student {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/completedStudents")
    public ResponseEntity<?> getAllCompletedStudents(){

        ApiResponse<?> response = studentService.getCompletedStudents();
        if (response.isSuccess()) log.warn("Getting All Completed Students -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{status}Students")
    public ResponseEntity<?> getAllStudentsByStatus(@PathVariable String status){

        ApiResponse<?> response = studentService.getAllStudentsByStatus(Status.valueOf(status));
        if (response.isSuccess()) log.warn("Getting All " + status + " Students -> {}", response.getData());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}