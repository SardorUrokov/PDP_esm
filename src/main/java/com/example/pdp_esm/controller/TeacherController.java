package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.ApiResponse;
import com.example.pdp_esm.dto.ResGroupDTO;
import com.example.pdp_esm.dto.TeacherDTO;
import com.example.pdp_esm.service.Implements.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherServiceImpl teacherService;

    @PostMapping("/teacher/create")
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDTO teacherDTO){
        ApiResponse<?> response = teacherService.create(teacherDTO);
        if (response.isSuccess()) log.warn("Teacher Created! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/teacher")
    public ResponseEntity<?> getAllTeachers(){

        ApiResponse<?> response = teacherService.getAll();
        if (response.isSuccess()) log.warn("Getting All teachers! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}