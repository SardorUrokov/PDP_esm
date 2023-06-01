package com.example.pdp_esm.dto.result;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResGroupModule {

    List<ResExamResults> moduleExamResults;
    ResGroupDTO moduleGroup;
    String createdAt;
}