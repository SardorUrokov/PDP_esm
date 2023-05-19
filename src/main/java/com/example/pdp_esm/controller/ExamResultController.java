package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.ExamResultDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.ExamResultServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExamResultController {

    private final ExamResultServiceImpl resultService;

    @PostMapping("/examResult/create")
    public ResponseEntity<?> createQuestion(@RequestBody ExamResultDTO resultDTO) {
        ApiResponse<?> response = resultService.createExamResult(resultDTO);

        if (response.isSuccess()) log.warn("ExamResult Saved! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/examResult")
    public ResponseEntity<?> getAllQuestions(){
        ApiResponse<?> response = resultService.getAllExamResult();

        if (response.isSuccess()) log.warn("All Exam Results List! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess()? 200 : 409).body(response);
    }

    @GetMapping("/examResult/{id}")
    public ResponseEntity<?> getOneQuestion(@PathVariable Long id){
        ApiResponse<?> response = resultService.getOneExamResult(id);

        if (response.isSuccess()) log.warn("Getting Exam Result {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/examResult/{id}")
    public ResponseEntity<?> updateExamResult (@PathVariable Long id, @RequestBody ExamResultDTO resultDTO){
        ApiResponse<?> response = resultService.updateExamResult(id, resultDTO);

        if (response.isSuccess()) log.warn("Exam Result Updated! {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/examResult/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id){
        ApiResponse<?> response = resultService.deleteExamResult(id);

        if (response.isSuccess()) log.warn("Exam Result Deleted! {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}