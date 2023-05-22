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
public class DeleteRequestDTO {

    @NotNull(message = "O'chirish uchun data ing ID sini kiriting")
    Long id;

    @Length(min = 10, max = 100)
    @NotNull(message = "O'chirish uchun sabab kiriting")
    String description;
}