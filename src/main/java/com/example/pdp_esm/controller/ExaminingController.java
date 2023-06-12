package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.ExamineInfoDTO;
import com.example.pdp_esm.dto.ExaminingDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.ExamineInfoServiceImpl;
import com.example.pdp_esm.service.Implements.ExaminingService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/examining")
public class ExaminingController {

    private final ExaminingService examiningService;
    private final ExamineInfoServiceImpl examineInfoService;

    @PostMapping("/createInfo")
    public ResponseEntity<?> createExamInfo(@RequestBody ExamineInfoDTO examineInfoDTO){
        ApiResponse<?> response = examineInfoService.create(examineInfoDTO);

        if (response.isSuccess()) log.warn("Creating Exam Info -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody ExaminingDTO examiningDTO) {
        ApiResponse<?> response = examiningService.calculateResult(examiningDTO);

        if (response.isSuccess()) log.warn("ExamResult Calculated! -> {}", response.getData());
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

    @GetMapping("/info/{id}")
    public ResponseEntity<?> gettingExamineInfoById(@PathVariable Long id){
        ApiResponse<?> response = examineInfoService.readOne(id);

        if (response.isSuccess()) log.warn("Getting ExamResultInfo by {} id -> {}", id, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/info/{date}")
    public ResponseEntity<?> gettingExamineInfoByDate(@PathVariable String date){
        ApiResponse<?> response = examineInfoService.byStartsDate(date);

        if (response.isSuccess()) log.warn("Getting ExamInfo by date {} -> {}", date, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateExamineInfo(@PathVariable Long id,@RequestBody ExamineInfoDTO infoDTO){
        ApiResponse<?> response = examineInfoService.update(id, infoDTO);

        if (response.isSuccess()) log.warn("Updating ExamInfo -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExamInfo (@PathVariable Long id){
        ApiResponse<?> response = examineInfoService.delete(id);

        if (response.isSuccess()) log.warn("Deleted ExamInfo by {} id -> {}", id, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}