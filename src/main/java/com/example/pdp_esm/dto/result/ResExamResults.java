package com.example.pdp_esm.dto.result;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResExamResults {

    private float score;
    private ResPaymentStudentInfo studentInfo;
    private String resultType, submitted_time;
    private List<ResQuestionDTO> questionList;
}