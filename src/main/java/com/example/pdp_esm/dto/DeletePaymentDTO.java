package com.example.pdp_esm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletePaymentDTO {

    @NotNull(message = "To'lovni o'chirish uchun uning ID sini kiriting")
    Long payment_id;

    @Length(min = 10, max = 100)
    @NotNull(message = "To'lovni o'chirish uchun sabab kiriting")
    String description;
}