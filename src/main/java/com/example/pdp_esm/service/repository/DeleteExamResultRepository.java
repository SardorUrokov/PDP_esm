package com.example.pdp_esm.service.repository;

import com.example.pdp_esm.entity.ExamResult;
import com.example.pdp_esm.entity.requests.DeleteRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteExamResultRepository extends JpaRepository<DeleteRequest<ExamResult>, Long> {
}
