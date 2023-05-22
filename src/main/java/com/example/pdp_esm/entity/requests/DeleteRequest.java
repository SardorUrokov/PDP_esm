package com.example.pdp_esm.entity.requests;

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
public class DeleteRequest<T> extends AbsEntity {

    String description;
    Boolean active;

    @OneToOne
    T object;
}