package com.example.pdp_esm.dto.result;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResDeletePaymentDTO {

    String description;
    String requestCreated_time;
    boolean active;
    ResPaymentDTO resPaymentDTO;
}