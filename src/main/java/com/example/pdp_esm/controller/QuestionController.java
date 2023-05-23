package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.QuestionDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.QuestionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
public class QuestionController {

    private final QuestionServiceImpl questionService;

    @PostMapping("/question/create")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionDTO questionDTO){
        ApiResponse<?> response = questionService.createQuestion(questionDTO);

        if (response.isSuccess()) log.warn("Question Created! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess()? 201 : 409).body(response);
    }

    @GetMapping("/question")
    public ResponseEntity<?> getAllQuestions(){
        ApiResponse<?> response = questionService.getAllQuestions();

        if (response.isSuccess()) log.warn("All Questions List! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess()? 200 : 409).body(response);
    }

    @GetMapping("/question/{active}")
    public ResponseEntity<?> getAllQuestionsByActive(@PathVariable String active){
        ApiResponse<?> response = questionService.getAllByActive(active);

        if (response.isSuccess()) log.warn("All Questions by List Active {} -> {}", active, response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess()? 200 : 409).body(response);
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<?> getOneQuestion(@PathVariable Long id){
        ApiResponse<?> response = questionService.getOneQuestion(id);

        if (response.isSuccess()) log.warn("Getting Question {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/question/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO){
        ApiResponse<?> response = questionService.updateQuestion(id, questionDTO);

        if (response.isSuccess()) log.warn("Question Updated! {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('MANAGER')")
    @DeleteMapping("/question/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id){
        ApiResponse<?> response = questionService.deleteQuestion(id);

        if (response.isSuccess()) log.warn("Question Deleted! {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}