package com.example.pdp_esm.dto.result;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResCourseDTOWithGroups {

    Long course_id;
    String courseName;
    double price;
    String courseType;
    boolean active;
    List<ResGroupDTO> groupList;
}