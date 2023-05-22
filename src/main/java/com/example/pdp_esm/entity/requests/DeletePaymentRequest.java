package com.example.pdp_esm.entity.requests;

import com.example.pdp_esm.entity.Payment;
import com.example.pdp_esm.entity.template.AbsEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletePaymentRequest extends AbsEntity {

    String description;
    Boolean active;

    @OneToOne
    Payment payment;
}