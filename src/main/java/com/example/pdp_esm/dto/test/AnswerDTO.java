package com.example.pdp_esm.dto.test;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerDTO {

    Long question_id;
    Boolean status;
    Integer position;
    String input;
}