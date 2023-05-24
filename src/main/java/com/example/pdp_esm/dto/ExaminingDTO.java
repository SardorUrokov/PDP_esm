package com.example.pdp_esm.dto;

import com.example.pdp_esm.dto.result.AttemptDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExaminingDTO {

    Long student_id;
    List<AttemptDTO> attempts;
}