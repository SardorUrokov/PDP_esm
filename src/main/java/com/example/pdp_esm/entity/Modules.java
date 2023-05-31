package com.example.pdp_esm.entity;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;
import com.example.pdp_esm.entity.template.AbsEntity;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Modules extends AbsEntity {

    Integer ordinalNumber;

    @ManyToOne
    Course course;

    @OneToMany
    List<GroupModule> eduModules;
}