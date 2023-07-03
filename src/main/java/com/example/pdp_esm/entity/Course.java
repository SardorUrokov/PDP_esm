package com.example.pdp_esm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.example.pdp_esm.entity.enums.CourseType;
import com.example.pdp_esm.entity.template.AbsEntity;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course extends AbsEntity {

    private String name;
    private double price;
    private boolean active;

    @Enumerated(value = EnumType.STRING)
    private CourseType courseType;

    public Course(String name, double price, CourseType courseType, boolean active) {
        this.name = name;
        this.price = price;
        this.courseType = courseType;
        this.active = active;
    }
    public Course(String name, double price, CourseType courseType) {
        this.name = name;
        this.price = price;
        this.courseType = courseType;
    }
}