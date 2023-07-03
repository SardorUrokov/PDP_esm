package com.example.pdp_esm.entity;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import lombok.experimental.FieldDefaults;
import com.example.pdp_esm.entity.template.AbsEntity;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Certificate extends AbsEntity {

    String certificateNumber;

    @OneToOne
    Course course;

    @ManyToOne
    Student student;
}