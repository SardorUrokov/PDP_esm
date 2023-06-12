package com.example.pdp_esm.entity;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamineInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer attemptsLimit;

    Date startsDate;

    @OneToMany
    Set<Course> courses;

    @OneToMany
    Set<Group> groups;
}