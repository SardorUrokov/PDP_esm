package com.example.pdp_esm.entity;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;
import com.example.pdp_esm.entity.enums.QuestionType;
import com.example.pdp_esm.entity.template.AbsEntity;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question extends AbsEntity {

    String questionText;

    @Enumerated(value = EnumType.STRING)
    QuestionType questionType;

    Boolean active;

    @ManyToOne
    Course course;
}