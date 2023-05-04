package com.example.pdp_esm.dto.result;

import com.example.pdp_esm.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Group> groupList;
}
