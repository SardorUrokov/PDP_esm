package com.example.pdp_esm.service.Implements;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.repository.AttemptsRepository;
import com.example.pdp_esm.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class AttemptsService {

    private final AttemptsRepository attemptsRepository;

    public boolean checkStudentAttempts (Long student_id, Long module_id){

        final var attempts = attemptsRepository.findByStudent_IdAndModules_Id(student_id, module_id)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt", "student_id", student_id));

        final var temp = attempts.getAttempts();
        return temp != 0;
    }

    public ApiResponse<?> updateAttempts (Long student_id, Long module_id){

        final var attempt = attemptsRepository
                .findByStudent_IdAndModules_Id(student_id, module_id)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt", "student_id", student_id));

        Integer temp = attempt.getAttempts();

        if (temp != null && temp != 0)
            temp -= 1;

        attempt.setAttempts(temp);
        attemptsRepository.save(attempt);

        return ApiResponse.builder()
                .message("Student Attempts Updated!")
                .success(true)
                .data(attempt)
                .build();
    }
}