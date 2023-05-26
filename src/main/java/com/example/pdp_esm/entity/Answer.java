package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.template.AbsEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer extends AbsEntity {

    String true_answer, answer1, answer2, answer3, answer4;
    boolean active;

    public Answer(String true_answer, String answer1, boolean active) {
        this.true_answer = true_answer;
        this.answer1 = answer1;
        this.active = active;
    }

    public Answer(String true_answer, String answer1, String answer2, boolean active) {
        this.true_answer = true_answer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.active = active;
    }

    public Answer(String true_answer, String answer1, String answer2, String answer3, boolean active) {
        this.true_answer = true_answer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.active = active;
    }

//    public Answer(String true_answer, String answer1, String answer2, String answer3, String answer4, boolean active) {
//        this.true_answer = true_answer;
//        this.answer1 = answer1;
//        this.answer2 = answer2;
//        this.answer3 = answer3;
//        this.answer4 = answer4;
//        this.active = active;
//    }
}