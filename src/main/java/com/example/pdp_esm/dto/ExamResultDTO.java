package com.example.pdp_esm.dto;

import com.example.pdp_esm.entity.enums.ResultType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamResultDTO {

    private float score;
    private Long studentId;
    private ResultType resultType;
    private List<Long> questionsIdList;
}