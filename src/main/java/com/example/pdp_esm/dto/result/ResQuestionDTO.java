package com.example.pdp_esm.dto.result;

import com.example.pdp_esm.dto.AnswerDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResQuestionDTO {

    String courseName, questionType, question;
    List<AnswerDTO> answerDTOs;
}