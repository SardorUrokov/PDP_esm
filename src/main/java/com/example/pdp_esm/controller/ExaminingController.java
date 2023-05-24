package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.ExaminingDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.ExaminingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/examining")
public class ExaminingController {

    private final ExaminingService examiningService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate (@RequestBody ExaminingDTO examiningDTO){
        ApiResponse<?> response = examiningService.calculateResult(examiningDTO);

        if (response.isSuccess()) log.warn("ExamResult Calculated! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
