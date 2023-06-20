package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.ExamineInfoDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.ExamineInfoServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExamineInfoController {

    private final ExamineInfoServiceImpl examineInfoService;
    @PostMapping("/info/create")
    public ResponseEntity<?> createExamInfo(@RequestBody ExamineInfoDTO examineInfoDTO){
        ApiResponse<?> response = examineInfoService.create(examineInfoDTO);

        if (response.isSuccess()) log.warn("Creating Exam Info -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/examInfo")
    public ResponseEntity<?> gettingExamineInfo(){

        ApiResponse<?> response = examineInfoService.readAll();

        if (response.isSuccess()) log.warn("Getting ExamResultInfo! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/infoById/{id}")
    public ResponseEntity<?> gettingExamineInfoById(@PathVariable Long id){
        ApiResponse<?> response = examineInfoService.readOne(id);

        if (response.isSuccess()) log.warn("Getting ExamResultInfo by {} id -> {}", id, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/infoByDate/{date}")
    public ResponseEntity<?> gettingExamineInfoByDate(@PathVariable String date){
        ApiResponse<?> response = examineInfoService.byStartsDate(date);

        if (response.isSuccess()) log.warn("Getting ExamInfo by date {} -> {}", date, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/infoByExamType/{type}")
    public ResponseEntity<?> gettingExamineInfoByExamType(@PathVariable String type){
        ApiResponse<?> response = examineInfoService.byExamType(type);

        if (response.isSuccess()) log.warn("Getting ExamInfo by {} Exam Type -> {}", type, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/info/update/{id}")
    public ResponseEntity<?> updateExamineInfo(@PathVariable Long id,@RequestBody ExamineInfoDTO infoDTO){
        ApiResponse<?> response = examineInfoService.update(id, infoDTO);

        if (response.isSuccess()) log.warn("Updating ExamInfo -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/info/delete/{id}")
    public ResponseEntity<?> deleteExamInfo (@PathVariable Long id){
        ApiResponse<?> response = examineInfoService.delete(id);

        if (response.isSuccess()) log.warn("Deleted ExamInfo by {} id ", id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}