package com.example.pdp_esm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne
    private Course course;

    @ManyToMany
    private List<Teacher> teacher;

    public Group(String groupName, Course course, List<Teacher> teacher) {
        this.groupName = groupName;
        this.course = course;
        this.teacher = teacher;
    }
}
