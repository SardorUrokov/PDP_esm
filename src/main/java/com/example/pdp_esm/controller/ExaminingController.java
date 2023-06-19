package com.example.pdp_esm.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.example.pdp_esm.dto.ExamineInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.dto.CheckingAttemptsDTO;
import com.example.pdp_esm.service.Implements.ExaminingService;
import com.example.pdp_esm.service.Implements.ExamineInfoServiceImpl;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/examining")
@PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
public class ExaminingController {

    private final ExaminingService examiningService;
    private final ExamineInfoServiceImpl examineInfoService;

    @PostMapping("/info/create")
    public ResponseEntity<?> createExamInfo(@RequestBody ExamineInfoDTO examineInfoDTO){
        ApiResponse<?> response = examineInfoService.create(examineInfoDTO);

        if (response.isSuccess()) log.warn("Creating Exam Info -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

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

    @GetMapping("/info")
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