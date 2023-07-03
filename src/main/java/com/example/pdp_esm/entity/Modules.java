package com.example.pdp_esm.entity;

import lombok.*;
import java.util.List;
import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;
import com.example.pdp_esm.entity.template.AbsEntity;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Modules extends AbsEntity {

    Long ordinalNumber;

    String name;

    @OneToOne
    AbstractModule abstractModule;

    @OneToMany(fetch = FetchType.EAGER)
    List<GroupModule> groupModules;
}