package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.CalculationInterval;
import com.example.pdp_esm.dto.PaymentDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.Payment;

import java.text.ParseException;

public interface PaymentService {

    ApiResponse<?> createPayment (PaymentDTO paymentDTO);

    ApiResponse<?> getAllPayments ();

    ApiResponse<?> calculateSumOfPayments(CalculationInterval interval) throws ParseException;

    ApiResponse<?> getOnePayment (Long payment_id);

    ApiResponse<?> deletePayment (Long payment_id);

    ApiResponse<?> getAllPaymentsByStatus(String status);
}