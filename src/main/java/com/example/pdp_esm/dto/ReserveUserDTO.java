package com.example.pdp_esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveUserDTO {

    private String phoneNumber, email, password, otpCode;

}