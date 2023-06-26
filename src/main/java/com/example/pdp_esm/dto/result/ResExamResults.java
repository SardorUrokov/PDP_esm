package com.example.pdp_esm.dto.result;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResExamResults {

    Long examResult_id;
    float score;
    ResPaymentStudentInfo studentInfo;
    String resultType, submitted_time;
    List<ResQuestionDTO> questionList;
}