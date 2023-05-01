package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.enums.PayType;
import com.example.pdp_esm.entity.template.AbsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends AbsEntity {

    private double amount;

    @OneToOne
    private Student student;

    @Enumerated(value = EnumType.STRING)
    private PayType payType;
}
