package com.example.pdp_esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {

    private Long courseId;
    private String question, true_answer, wrong_answer1, wrong_answer2, wrong_answer3;

}