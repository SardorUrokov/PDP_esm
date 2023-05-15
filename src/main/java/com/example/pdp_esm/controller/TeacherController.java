package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.TeacherDTO;
import com.example.pdp_esm.service.Implements.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherServiceImpl teacherService;

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @PostMapping("/teacher/create")
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        ApiResponse<?> response = teacherService.createTeacher(teacherDTO);
        if (response.isSuccess()) log.warn("Teacher Created! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/teacher")
    public ResponseEntity<?> getAllTeachers() {

        ApiResponse<?> response = teacherService.getAllTeachers();
        if (response.isSuccess()) log.warn("Getting All teachers! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<?> getOneTeacher(@PathVariable Long id) {

        ApiResponse<?> response = teacherService.getOneTeacher(id);
        if (response.isSuccess()) log.warn("Getting Teacher {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/teacher/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id, @RequestBody TeacherDTO teacherDTO) {
        ApiResponse<?> response = teacherService.updateTeacher(id, teacherDTO);

        if (response.isSuccess()) log.warn("Teacher Updated {} with id! -> {}", response.getData(), id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/deleteTeacher/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {

        ApiResponse<?> response = teacherService.deleteTeacher(id);
        if (response.isSuccess()) log.warn("Teacher Removed {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/removedTeacher")
    public ResponseEntity<?> getAllRemovedTeacher(){
        ApiResponse<?> response = teacherService.getAllActiveFalseTeachers();
        log.warn("Getting All Removed Teachers! -> {}", response.getData());
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/removedTeacher/{id}")
    public ResponseEntity<?> getOneRemovedTeacher(@PathVariable Long id){
        ApiResponse<?> response = teacherService.getOneActiveFalseTeacher(id);
        if (response.isSuccess()) log.warn("Getting One Removed Teacher {} with id! -> {}", response.getData(), id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}