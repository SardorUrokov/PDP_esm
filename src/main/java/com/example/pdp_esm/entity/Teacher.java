package com.example.pdp_esm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName, phone;
    private boolean gender;

    @ManyToOne
    private Position position;

    @ManyToMany
    private List<Course> course;

    public Teacher(String fullName, String phone, boolean gender, Position position, List<Course> course) {
        this.fullName = fullName;
        this.phone = phone;
        this.gender = gender;
        this.position = position;
        this.course = course;
    }
}