package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.enums.ResultType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float score;

    @OneToOne
    private Student student;

    @Enumerated(value = EnumType.STRING)
    private ResultType resultType;

//    @ManyToMany(mappedBy = "question")
    @ManyToMany
    private List<Question> questionList;

    public ExamResult(float score, Student student, ResultType resultType, List<Question> questionList) {
        this.score = score;
        this.student = student;
        this.resultType = resultType;
        this.questionList = questionList;
    }
}