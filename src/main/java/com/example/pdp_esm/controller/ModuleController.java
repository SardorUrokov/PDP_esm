package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.ModuleDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.ModuleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/module")
public class ModuleController {

    private final ModuleServiceImpl moduleService;

    @PostMapping("/create")
    public ResponseEntity<?> createModule(@RequestBody ModuleDTO moduleDTO){
        ApiResponse<?> response = moduleService.createModule(moduleDTO);

        if (response.isSuccess()) log.warn("Module Created! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllModules(){
        ApiResponse<?> response = moduleService.getAllModule();

        if (response.isSuccess()) log.warn("Getting Modules List! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("byModuleId/{id}")
    public ResponseEntity<?> getByModuleId(@PathVariable Long id){
        ApiResponse<?> response = moduleService.getByModuleId(id);

        if (response.isSuccess()) log.warn("Getting Module with {} id! -> {}", id, response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("byCourseId/{id}")
    public ResponseEntity<?> getByCourseId(@PathVariable Long id){
        ApiResponse<?> response = moduleService.getByCourseId(id);

        if (response.isSuccess()) log.warn("Getting Module by {} CourseId! -> {}", id, response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("byOrdinalNum/{num}")
    public ResponseEntity<?> getByOrdinalNum(@PathVariable Long num){
        ApiResponse<?> response = moduleService.getByOrdinalNumber(num);

        if (response.isSuccess()) log.warn("Getting Module by {} Ordinal Number! -> {}", num, response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateModule(@PathVariable Long id, @RequestBody ModuleDTO moduleDTO){
        ApiResponse<?> response = moduleService.updateModule(id, moduleDTO);

        if (response.isSuccess()) log.warn("Module with {} id is Updated! -> {}", id, response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/deleteModule/{id}")
    public ResponseEntity<?> deleteModule(@PathVariable Long id){
        ApiResponse<?> response = moduleService.deleteModule(id);

        if (response.isSuccess()) log.warn("Module Deleted with {} id! -> {}", id, response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}