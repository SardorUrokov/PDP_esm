package com.example.pdp_esm.dto.result;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResExamineInfoDTO{

    Integer attempts, numOfQuestions;
    String examName, startsDate, examType;
    ResModule module;
    Set<ResCourseDTOWithGroups> coursesWithGroups;
    List<ResGroupDTO> groupDTOS;

}