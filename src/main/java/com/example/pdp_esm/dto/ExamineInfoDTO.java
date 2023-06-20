package com.example.pdp_esm.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamineInfoDTO {

    Integer attempts, numOfQuestions;
    String startsDate, examType;
    Long moduleId;
    List<Long> coursesIds;
    List<Long> groupsIds;
}