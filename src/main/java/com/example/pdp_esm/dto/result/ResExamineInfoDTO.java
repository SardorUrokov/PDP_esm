package com.example.pdp_esm.dto.result;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResExamineInfoDTO{

    Long examineInfoId;
    Integer attempts, numOfQuestions;
    String examName, startsDate, examType;
    ResModule module;
    Set<ResCourseDTOWithGroups> coursesWithGroups;
    List<ResGroupDTO> groupDTOS;

}