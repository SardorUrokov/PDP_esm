package com.example.pdp_esm.entity.requests;

import com.example.pdp_esm.entity.ExamResult;
import com.example.pdp_esm.entity.template.AbsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteExamResultRequest extends AbsEntity {

    String description;
    Boolean active;

    @OneToOne
    ExamResult examResult;
}