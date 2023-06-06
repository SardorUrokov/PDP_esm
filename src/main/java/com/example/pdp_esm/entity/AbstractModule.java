package com.example.pdp_esm.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import com.example.pdp_esm.entity.template.AbsEntity;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbstractModule extends AbsEntity {

    Long modules;

    @OneToOne
    Course course;
}