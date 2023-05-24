package com.example.pdp_esm.dto.result;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResModuleDTO {

    String ordinalNumber, moduleName;
    List<ResExamResults> moduleExamResults;
    List<ResGroupDTO> moduleGroups;
}