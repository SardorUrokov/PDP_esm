package com.example.pdp_esm.auth;

import com.example.pdp_esm.entity.enums.Roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {

    String token;
    Roles role;
    String username;
}