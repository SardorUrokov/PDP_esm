package com.example.pdp_esm.dto.result;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResGroupDTO {

    Long group_id;
    String groupName, courseName, courseType, startsDate;
    List<ResTeacherDTO> teachers;
    List<ResStudentDTO> students;
    boolean active;
}