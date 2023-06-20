package com.example.pdp_esm.entity;

import lombok.*;

import java.util.Set;
import java.util.Date;

import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;
import com.example.pdp_esm.entity.enums.ExamType;

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

    Integer attemptsLimit, numOfQuestions;

    String examName;

    Date startsDate;

    @OneToOne
    Modules module;

    @Enumerated(value = EnumType.STRING)
    ExamType examType;

    @OneToMany
    Set<Course> courses;

    @OneToMany
    Set<Group> groups;
}