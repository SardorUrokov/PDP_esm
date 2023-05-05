package com.example.pdp_esm.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

//    @Timestamp
//    @Temporal(value = TemporalType.TIMESTAMP)
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