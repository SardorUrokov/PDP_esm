package com.example.pdp_esm.repository.deleteRequests;

import com.example.pdp_esm.entity.requests.DeleteExamResultRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeleteExamResultRepository extends JpaRepository<DeleteExamResultRequest, Long> {

    Optional<DeleteExamResultRequest> findByExamResult_Id(Long examResult_id);
    void deleteByExamResult_Id(Long examResult_id);
}