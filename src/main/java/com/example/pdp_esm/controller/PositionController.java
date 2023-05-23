package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.PositionServiceImpl;
import com.example.pdp_esm.service.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
public class PositionController {

    private final PositionServiceImpl positionService;

    @PreAuthorize(value = "hasAnyAuthority('MANAGER')")

    @PostMapping("/position/create+{name}")
    public ResponseEntity<?> createPosition(@PathVariable String name){
        ApiResponse<?> response = positionService.createPosition(name);
        if (response.isSuccess()) log.warn("Position Created -> {}", response.getData());
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/position")
    public ResponseEntity<?> getAllPositions(){
        ApiResponse<?> response = positionService.getAllPositions();
        if (response.isSuccess()) log.warn("Getting All Position -> {}", response.getData());
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/position/{id}")
    public ResponseEntity<?> getOnePosition(@PathVariable Long id){
        ApiResponse<?> response = positionService.getOnePosition(id);
        if (response.isSuccess()) log.warn("Getting One Position with {} id -> {}", id, response.getData());
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('MANAGER')")
    @DeleteMapping("/deletePosition/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable Long id){
        ApiResponse<?> response = positionService.deletePosition(id);
        if (response.isSuccess()) log.warn("Delete Position with {} id -> {}", id, response.getData());
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
