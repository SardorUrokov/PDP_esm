package com.example.pdp_esm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne
    private Course course;

    @ManyToOne
    private Teacher teacher;

    public Group(String groupName, Course course, Teacher teacher) {
        this.groupName = groupName;
        this.course = course;
        this.teacher = teacher;
    }
}
