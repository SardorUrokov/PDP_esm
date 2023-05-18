package com.example.pdp_esm.dto.result;

import com.example.pdp_esm.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResExamResults {

    private float score;
    private ResPaymentStudentInfo studentInfo;
    private String resultType;
    private List<Question> questionList;
}
