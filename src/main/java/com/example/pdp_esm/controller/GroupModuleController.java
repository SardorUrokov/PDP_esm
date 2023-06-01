package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.EduModuleDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.GroupModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/eduModule")
public class GroupModuleController {

    private final GroupModuleService eduModuleService;

    @PostMapping("/create")
    public ResponseEntity<?> createModule(@RequestBody EduModuleDTO moduleDTO){
        ApiResponse<?> response = eduModuleService.createModule(moduleDTO);

        if (response.isSuccess()) log.warn("Group Module Created! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllModules(){
        ApiResponse<?> response = eduModuleService.getAllModules();

        if (response.isSuccess()) log.warn("Getting Group Modules List! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne (@PathVariable Long id){
        ApiResponse<?> response = eduModuleService.getOne(id);

        if (response.isSuccess()) log.warn("Getting Group Module with {} id! -> {}", id, response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEduModule(@PathVariable Long id, @RequestBody EduModuleDTO moduleDTO){
        ApiResponse<?> response = eduModuleService.updateModule(id, moduleDTO);

        if (response.isSuccess()) log.warn("Group Module with {} id is Updated! -> {}", id, response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/deleteModule/{id}")
    public ResponseEntity<?> deleteModule(@PathVariable Long id){
        ApiResponse<?> response = eduModuleService.deleteModule(id);

        if (response.isSuccess()) log.warn("Delete Group Module with {} id! -> {}", id, response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}