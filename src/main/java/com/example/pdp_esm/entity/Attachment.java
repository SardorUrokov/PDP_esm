package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.template.AbsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment extends AbsEntity {

    String name, type;

    @Lob
    byte[] bytes;

}
