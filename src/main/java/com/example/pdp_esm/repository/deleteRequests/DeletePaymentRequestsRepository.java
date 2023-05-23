package com.example.pdp_esm.repository.deleteRequests;

import com.example.pdp_esm.entity.Payment;
import com.example.pdp_esm.entity.enums.PayStatus;
import com.example.pdp_esm.entity.requests.DeletePaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeletePaymentRequestsRepository extends JpaRepository<DeletePaymentRequest, Long> {

//    Optional<DeletePaymentRequest> findByPayStatus (PayStatus payStatus);
}