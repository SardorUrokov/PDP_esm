package com.example.pdp_esm.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResModule {

    Long ordinalNumber;
    String courseName;
    List<ResGroupModule> groupModuleList;
}