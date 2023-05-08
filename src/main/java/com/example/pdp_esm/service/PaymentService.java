package com.example.pdp_esm.service;

import com.example.pdp_esm.dto.PaymentDTO;
import com.example.pdp_esm.dto.result.ApiResponse;

public interface PaymentService {

    ApiResponse<?> createPayment (PaymentDTO paymentDTO);

    ApiResponse<?> getAllPayments ();

    ApiResponse<?> getOnePayment (Long payment_id);

    ApiResponse<?> deletePayment (Long payment_id);
}