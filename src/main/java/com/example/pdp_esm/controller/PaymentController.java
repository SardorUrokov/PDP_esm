package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.PaymentDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    @PostMapping("/payment/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO){
        ApiResponse<?> response = paymentService.createPayment(paymentDTO);

        if (response.isSuccess()) log.warn("Payment Created! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/payment")
    public ResponseEntity<?> getAllPayments(){
        ApiResponse<?> response = paymentService.getAllPayments();
        log.warn("Getting All Payments List! -> {}", response.getData());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<?> getOnePayment(@PathVariable Long id){
        ApiResponse<?> response = paymentService.getOnePayment(id);
        if (response.isSuccess()) log.warn("Getting Payment {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/payment/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id){

        ApiResponse<?> response = paymentService.deletePayment(id);

        if (response.isSuccess()) log.warn("Payment Deleted {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
