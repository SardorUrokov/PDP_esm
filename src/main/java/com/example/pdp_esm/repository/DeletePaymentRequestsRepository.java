package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.requests.DeletePaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeletePaymentRequestsRepository extends JpaRepository<DeletePaymentRequest, Long> {

    Optional<DeletePaymentRequest> findByPaymentId(Long payment_id);
}
