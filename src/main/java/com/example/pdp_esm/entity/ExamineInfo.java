package com.example.pdp_esm.entity;

import lombok.*;

import java.util.List;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    LocalDateTime startsDate;

    @OneToMany
    List<Course> course;

    @OneToMany
    List<Group> groups;
}