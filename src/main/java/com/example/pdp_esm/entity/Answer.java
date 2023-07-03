package com.example.pdp_esm.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import com.example.pdp_esm.entity.template.AbsEntity;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer extends AbsEntity {

    String body;
    boolean status;
    Integer position;

    @OneToOne
    Question question;
}