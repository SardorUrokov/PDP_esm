package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.AnswerDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.AnswerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
@PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
public class AnswerController {

    private final AnswerServiceImpl answerService;

    @PostMapping("/create")
    public ResponseEntity<?> createAnswer(@RequestBody AnswerDTO answerDTO) {
        ApiResponse<?> response = answerService.createAnswer(answerDTO);

        if (response.isSuccess()) log.warn("Answer is created! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess()? 201 : 409).body(response);
   }

    @GetMapping("/")
    public ResponseEntity<?> getAllAnswers(){
        ApiResponse<?> response = answerService.getAllAnswers();

        if (response.isSuccess()) log.warn("All Answers List! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess()? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneAnswer(@PathVariable Long id){
        ApiResponse<?> response = answerService.getOneAnswer(id);

        if (response.isSuccess()) log.warn("Getting Answer with {} id! -> {}", id, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnswer(@PathVariable Long id, @RequestBody AnswerDTO answerDTO){
        ApiResponse<?> response = answerService.updateAnswer(id, answerDTO);

        if (response.isSuccess()) log.warn("Answer Updated with {} id! -> {}", id, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long id){
        ApiResponse<?> response = answerService.deleteAnswer(id);

        if (response.isSuccess()) log.warn("Answer Deleted! with {} id! -> {}", id, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}