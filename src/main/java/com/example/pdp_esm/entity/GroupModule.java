package com.example.pdp_esm.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;
import com.example.pdp_esm.entity.template.AbsEntity;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupModule extends AbsEntity {

    @OneToMany
    List<ExamResult> examResults;

    @OneToOne
    Group group;
}