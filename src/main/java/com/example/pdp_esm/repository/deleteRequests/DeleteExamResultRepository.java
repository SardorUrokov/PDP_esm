package com.example.pdp_esm.repository.deleteRequests;

import com.example.pdp_esm.entity.requests.DeleteExamResultRequest;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DeleteExamResultRepository extends JpaRepository<DeleteExamResultRequest, Long> {
}