package com.example.pdp_esm.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.example.pdp_esm.dto.AbsModuleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.AbsModuleServiceImpl;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/courseModule")
public class AbsModuleController {

    private final AbsModuleServiceImpl absModuleService;

    @PostMapping("/create")
    public ResponseEntity<?> createCourseModule(@RequestBody AbsModuleDTO absModuleDTO) {
        final ApiResponse<?> response = absModuleService.createAbsModule(absModuleDTO);

        if (response.isSuccess()) log.warn("Course Module is created! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<?> gatAllCourseModules() {
        final ApiResponse<?> response = absModuleService.getAllAbsModules();

        if (response.isSuccess()) log.warn("Course Module is created! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getCourseModuleById(@PathVariable Long id) {
        final ApiResponse<?> response = absModuleService.getOneAbsModule(id);

        if (response.isSuccess()) log.warn("Getting CourseModule by {} id -> {}", id, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/getByCourseId/{id}")
    public ResponseEntity<?> getCourseModuleByCourseId(@PathVariable Long id) {
        final ApiResponse<?> response = absModuleService.getByCourseId(id);

        if (response.isSuccess()) log.warn("Getting CourseModule by {} courseId -> {}", id, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/getByModule/{id}")
    public ResponseEntity<?> getByModule(@PathVariable Long id) {
        final ApiResponse<?> response = absModuleService.getByModule(id);

        if (response.isSuccess()) log.warn("Getting CourseModule by {} module -> {}", id, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/updateModule/{id}")
    public ResponseEntity<?> updateCourseModule(@PathVariable Long id, @RequestBody AbsModuleDTO absModuleDTO) {
        final ApiResponse<?> response = absModuleService.updateAbsModule(id, absModuleDTO);

        if (response.isSuccess()) log.warn("Course Module Updated! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourseModule(@PathVariable Long id) {
        final ApiResponse<?> response = absModuleService.deleteAbsModule(id);

        if (response.isSuccess()) log.warn("Course Module deleted! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}