package com.example.pdp_esm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Course course;

    private String question, true_answer, wrong_answer1, wrong_answer2, wrong_answer3;

    public Question(Course course, String question, String true_answer, String wrong_answer1, String wrong_answer2, String wrong_answer3) {
        this.course = course;
        this.question = question;
        this.true_answer = true_answer;
        this.wrong_answer1 = wrong_answer1;
        this.wrong_answer2 = wrong_answer2;
        this.wrong_answer3 = wrong_answer3;
    }
}