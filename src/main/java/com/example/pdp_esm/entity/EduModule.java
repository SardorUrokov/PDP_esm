package com.example.pdp_esm.entity;

import lombok.*;
import java.util.List;
import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;

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