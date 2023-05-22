package com.example.pdp_esm.repository.deleteRequests;

import com.example.pdp_esm.entity.Question;
import com.example.pdp_esm.entity.requests.DeleteRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteQuestionRequestRepository extends JpaRepository<DeleteRequest<Question>, Long> {
}
