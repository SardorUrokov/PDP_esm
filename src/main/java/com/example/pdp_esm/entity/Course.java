package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.enums.CourseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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