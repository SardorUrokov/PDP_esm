package com.example.pdp_esm.dto.result;

import com.example.pdp_esm.dto.test.AnswerDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamineQuestionsDTO {

    ResQuestionDTO questionDTO;
    AnswerDTO answerDTO;
}