package com.example.pdp_esm.dto.result;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResCertificateDTO {

    String createdAt, certificateId, courseName, studentFullName;
}