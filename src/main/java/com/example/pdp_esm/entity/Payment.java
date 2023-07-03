package com.example.pdp_esm.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import com.example.pdp_esm.entity.enums.PayType;
import com.example.pdp_esm.entity.enums.PayStatus;
import com.example.pdp_esm.entity.template.AbsEntity;

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

    @Enumerated(value = EnumType.STRING)
    private PayStatus payStatus;
}