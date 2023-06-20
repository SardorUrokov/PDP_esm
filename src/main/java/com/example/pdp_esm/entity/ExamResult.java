package com.example.pdp_esm.entity;

import java.util.List;
import jakarta.persistence.*;
import com.example.pdp_esm.entity.enums.ResultType;
import com.example.pdp_esm.entity.template.AbsEntity;

import lombok.*;
import lombok.experimental.FieldDefaults;

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