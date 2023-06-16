package com.example.pdp_esm.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckingAttemptsDTO {

    Long student_id;
    List<AnswerObject> selectedAnswers;
}