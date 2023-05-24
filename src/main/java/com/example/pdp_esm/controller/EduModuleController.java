package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.EduModuleDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.EduModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/eduModule")
public class EduModuleController {

    private final EduModuleService eduModuleService;

    @PostMapping("/create")
    public ResponseEntity<?> createModule(@RequestBody EduModuleDTO moduleDTO){
        ApiResponse<?> response = eduModuleService.createModule(moduleDTO);

        if (response.isSuccess()) log.warn("EduModule Created! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllModules(){
        ApiResponse<?> response = eduModuleService.getAllModules();

        if (response.isSuccess()) log.warn("Getting EduModules List! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllModules(@PathVariable Long id){
        ApiResponse<?> response = eduModuleService.getOne(id);

        if (response.isSuccess()) log.warn("Getting EduModule with {} id! -> {}", id, response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

//    public ResponseEntity<?> update

    @DeleteMapping("/deleteModule/{id}")
    public ResponseEntity<?> deleteModule(@PathVariable Long id){
        ApiResponse<?> response = eduModuleService.deleteModule(id);

        if (response.isSuccess()) log.warn("Delete EduModule with {} id! -> {}", id, response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}