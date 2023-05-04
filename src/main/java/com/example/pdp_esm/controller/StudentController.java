package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.ApiResponse;
import com.example.pdp_esm.service.Implements.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;
    @GetMapping("/student")
    public ResponseEntity<?> getAllStudents(){

        ApiResponse<?> response = studentService.getAll();
        if (response.isSuccess()) log.warn("Getting All Students! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}