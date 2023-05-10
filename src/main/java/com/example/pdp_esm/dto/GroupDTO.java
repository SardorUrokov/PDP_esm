package com.example.pdp_esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {

    private String groupName;
    private Long courseId;
    private List<Long> teacherIds;
    private String startsDate;
}