package com.example.pdp_esm.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerDTO {

    String true_answer, answer1, answer2, answer3, answer4;
    public AnswerDTO(String true_answer) {
        this.true_answer = true_answer;
    }

    public AnswerDTO(String true_answer, String answer1) {
        this.true_answer = true_answer;
        this.answer1 = answer1;
    }

    public AnswerDTO(String true_answer, String answer1, String answer2) {
        this.true_answer = true_answer;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    public AnswerDTO(String true_answer, String answer1, String answer2, String answer3) {
        this.true_answer = true_answer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }
}