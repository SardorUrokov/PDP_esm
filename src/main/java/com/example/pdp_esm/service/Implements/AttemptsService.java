package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.Attempts;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.pdp_esm.repository.AttemptsRepository;

@Service
@RequiredArgsConstructor
public class AttemptsService {

    private final AttemptsRepository attemptsRepository;

    public boolean checkStudentAttempts (Long student_id, Long module_id){

        final var attempts = attemptsRepository.findByStudent_IdAndGroupModule_Id(student_id, module_id)
                .orElseThrow(() -> new ResourceNotFoundException("Attemp", "student_id", student_id));

        final var temp = attempts.getAttempts();
        return temp == null || temp != 0;
    }

    public ApiResponse<?> updateAttempts (Long student_id, Long module_id){

        final var optionalAttempts = attemptsRepository.findByStudent_IdAndGroupModule_Id(student_id, module_id);
        final var attempt = optionalAttempts.get();
        Integer temp = attempt.getAttempts();

        if (temp != null && temp != 0)
            temp -= 1;

        attempt.setAttempts(temp);
        attemptsRepository.save(attempt);

        return ApiResponse.builder()
                .message("Student Attemps Updated!")
                .success(true)
                .data(attempt)
                .build();
    }
}
