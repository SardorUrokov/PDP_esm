package com.example.pdp_esm.dto.result;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResDeleteExamResultDTO {

    Long id;
    String description, requestCreated_time;
    boolean active;
    ResExamResults examResults;
}