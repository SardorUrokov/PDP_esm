package com.example.pdp_esm.auth;

import com.example.pdp_esm.entity.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String fullName;
  private String email;
  private String password;
  private Roles role;
}