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
public class ResCourseDTOWithGroups {

    private String courseName;
    private double price;
    private String courseType;
    private boolean active;
    private List<ResGroupDTO> groupList;

}