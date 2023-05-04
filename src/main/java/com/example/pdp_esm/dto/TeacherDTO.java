package com.example.pdp_esm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {

    @NotNull(message = "FIO to'ldirilishi shart")
    private String fullName;

    @NotNull(message = "Telefon raqam kiritilishi shart")
    private String phoneNumber;

    private Long positionId;
    private List<Long> coursesIds;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email, password;

    private String gender;
}