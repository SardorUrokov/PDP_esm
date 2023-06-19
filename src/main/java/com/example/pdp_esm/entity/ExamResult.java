package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.enums.ResultType;
import com.example.pdp_esm.entity.template.AbsEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamResult extends AbsEntity {

    private float score;

    @OneToOne
    ExamineInfo examineInfo;

    @OneToOne
    private Student student;

    @Enumerated(value = EnumType.STRING)
    private ResultType resultType;

    @ManyToMany
    private List<Question> questionList;
}