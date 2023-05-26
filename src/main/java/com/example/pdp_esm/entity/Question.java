package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.enums.QuestionType;
import com.example.pdp_esm.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question extends AbsEntity {

    @ManyToOne
    private Course course;

    @Enumerated(value = EnumType.STRING)
    private QuestionType questionType;

    private String questionText;

    @OneToOne
    private Answer answer;

    private Boolean active;
}