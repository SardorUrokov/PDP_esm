package com.example.pdp_esm.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;
    private boolean active;

    @OneToOne
    private Course course;

    @ManyToMany
    private List<Teacher> teacher;

    private LocalDate startDate;

    public Group(String groupName, Course course, boolean active, List<Teacher> teacher, LocalDate startDate) {
        this.groupName = groupName;
        this.course = course;
        this.active = active;
        this.teacher = teacher;
        this.startDate = startDate;
    }

    public Group(String groupName, Course course, List<Teacher> teacher) {
        this.groupName = groupName;
        this.course = course;
        this.teacher = teacher;
    }
}