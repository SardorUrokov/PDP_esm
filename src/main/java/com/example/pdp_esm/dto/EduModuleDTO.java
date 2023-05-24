package com.example.pdp_esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EduModuleDTO {

    private Integer ordinalNumber = 1;
    private List<Long> examResultIds;
    private List<Long> groupIds;
}