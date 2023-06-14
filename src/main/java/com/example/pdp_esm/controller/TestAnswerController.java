package com.example.pdp_esm.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.example.pdp_esm.dto.test.AnswerDTO;
import org.springframework.http.ResponseEntity;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.test.AnswerService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/testAnswer")
public class TestAnswerController {

    private final AnswerService answerService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AnswerDTO answerDTO){
        final ApiResponse<?> response = answerService.createAnswer(answerDTO);

        if (response.isSuccess()) log.warn("Answer Created! {} -> ", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }


    @GetMapping("/")
    public ResponseEntity<?> getAll(){
        final ApiResponse<?> response = answerService.getAllAnswers();

        if (response.isSuccess()) log.warn("Getting All Answers! {} -> ", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        final ApiResponse<?> response = answerService.getOneAnswer(id);

        if (response.isSuccess()) log.warn("Getting Answer by {} id -> ", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update (@PathVariable Long id, @RequestBody AnswerDTO answerDTO){
        final ApiResponse<?> response = answerService.updateAnswer(id, answerDTO);

        if (response.isSuccess()) log.warn("Updating Answer by {} id -> ", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        final ApiResponse<?> response = answerService.deleteAnswer(id);

        if (response.isSuccess()) log.warn("Deleting Answer by {} id -> ", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}