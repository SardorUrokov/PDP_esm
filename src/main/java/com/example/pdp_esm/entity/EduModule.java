package com.example.pdp_esm.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EduModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer ordinalNumber = 1;
    String moduleName;

    @OneToMany
    List<ExamResult> examResults;

    @OneToMany
    List<Group> groups;
}