package com.example.pdp_esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private String courseName;
    private double price;
    private String courseType;
    private boolean active;

}

