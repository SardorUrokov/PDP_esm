package com.example.pdp_esm.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPaymentDTO {

    Long payment_id;
    private double amount;
    private String payType;
    private String payStatus;
    private String date;
    private ResPaymentStudentInfo studentInfo;

}


