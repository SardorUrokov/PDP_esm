package com.example.pdp_esm.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResStudentDTO {
    private String fullName, phoneNumber, email;
    private Double balance;
    private String gender, status, groupName;
}