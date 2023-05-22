package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.DeleteRequestDTO;
import com.example.pdp_esm.dto.PaymentDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
@PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) {
        ApiResponse<?> response = paymentService.createPayment(paymentDTO);

        if (response.isSuccess()) log.warn("Payment Created! -> {}", response);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    //    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/allPayments")
    public ResponseEntity<?> getAllPayments() {
        ApiResponse<?> response = paymentService.getAllPayments();
        log.warn("Getting All Payments List! -> {}", response.getData());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/allPayments/{status}")
    public ResponseEntity<?> getAllPaymentsByStatus(@PathVariable String status) {
        ApiResponse<?> response = paymentService.getAllPaymentsByStatus(status);

        if (response.isSuccess()) log.warn("Getting All Payments List by {} status! -> {}", status, response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOnePayment(@PathVariable Long id) {
        ApiResponse<?> response = paymentService.getOnePayment(id);
        if (response.isSuccess()) log.warn("Getting Payment {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @PostMapping("/deleteRequest")
    public ResponseEntity<?> deletePaymentRequest(@RequestBody DeleteRequestDTO dto) {
        ApiResponse<?> response = paymentService.createDeletePaymentRequest(dto);

        if (response.isSuccess())
            log.warn("Delete Payment Request Created with {} Payment id! -> {}", dto.getId(), response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('MANAGER')")
    @GetMapping("/deleteRequestsList")
    public ResponseEntity<?> deletePaymentRequestsList() {
        ApiResponse<?> response = paymentService.getAllDeletePaymentRequests();

        if (response.isSuccess()) log.warn("Delete Payment Requests List! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        ApiResponse<?> response = paymentService.deletePayment(id);

        if (response.isSuccess()) log.warn("Payment Deleted {} with id! -> {}", response.getData(), id);
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}