package com.example.pdp_esm.dto;

import com.example.pdp_esm.dto.result.ResPaymentDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SumOfPaymentsDTO {

    CalculationInterval interval;
    double sumOfIntervalPayments;
    List<ResPaymentDTO> resPaymentDTOList;

}