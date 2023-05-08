package com.example.pdp_esm.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPaymentStudentInfo {

    private String studentName, studentPhoneNumber, studentGroupName;
}
