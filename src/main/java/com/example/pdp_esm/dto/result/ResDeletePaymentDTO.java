package com.example.pdp_esm.dto.result;

import jakarta.validation.constraints.Null;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

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