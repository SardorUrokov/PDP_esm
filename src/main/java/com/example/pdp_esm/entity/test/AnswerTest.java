package com.example.pdp_esm.entity.test;

import jakarta.persistence.*;
import com.example.pdp_esm.entity.Question;
import com.example.pdp_esm.entity.template.AbsEntity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerTest extends AbsEntity {

    @OneToOne
    Question question;

    String body;
    boolean status;
    Integer position;
}