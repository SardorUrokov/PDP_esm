package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.QuestionDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.QuestionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionServiceImpl questionService;

    @PostMapping("/question/create")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionDTO questionDTO){
        ApiResponse<?> response = questionService.createQuestion(questionDTO);

        if (response.isSuccess()) log.warn("Question Created! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess()? 200 : 409).body(response);
    }

    @GetMapping("/question")
    public ResponseEntity<?> getAllQuestions(){
        ApiResponse<?> response = questionService.getAllQuestions();

        if (response.isSuccess()) log.warn("All Questions List! -> {}", response);
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

    @DeleteMapping("/question/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id){
        ApiResponse<?> response = questionService.deleteQuestion(id);

        if (response.isSuccess()) log.warn("Question Deleted! {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
