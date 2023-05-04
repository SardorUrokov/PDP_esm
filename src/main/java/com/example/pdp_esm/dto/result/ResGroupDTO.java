package com.example.pdp_esm.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResGroupDTO {

    private String groupName;
    private String courseName;
    private String courseType;
    private List<ResTeacherDTO> teachers;
    private List<ResStudentDTO> students;
    private boolean active;
}
