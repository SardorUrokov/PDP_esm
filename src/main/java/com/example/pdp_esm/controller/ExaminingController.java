package com.example.pdp_esm.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.CheckingAttemptsDTO;
import com.example.pdp_esm.service.Implements.ExaminingService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/examining")
@PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
public class ExaminingController {

    private final ExaminingService examiningService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody CheckingAttemptsDTO attemptsDTO) {
        ApiResponse<?> response = examiningService.checkingAnswers(attemptsDTO);

        if (response.isSuccess()) log.warn("ExamResult Calculated! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/getQuestions/{otp}")
    public ResponseEntity<?> getExamQuestions(@PathVariable String otp){
        final ApiResponse<?> response = examiningService.getExamQuestions(otp);

        if (response.isSuccess()) log.warn("Getting Exam Questions! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}