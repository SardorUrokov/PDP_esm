package com.example.pdp_esm.repository.deleteRequests;

import com.example.pdp_esm.entity.Payment;
import com.example.pdp_esm.entity.requests.DeleteRequest;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeletePaymentRequestsRepository extends JpaRepository<DeleteRequest<Payment>, Long> {

    @NonNull Optional<DeleteRequest<Payment>> findById (@NonNull Long payment_id);
}