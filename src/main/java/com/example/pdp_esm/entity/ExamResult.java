package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.enums.ResultType;
import com.example.pdp_esm.entity.template.AbsEntity;
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
public class ExamResult extends AbsEntity {

    private float score;

    @OneToOne
    private Student student;

    @Enumerated(value = EnumType.STRING)
    private ResultType resultType;

    @ManyToMany
    private List<Question> questionList;

}