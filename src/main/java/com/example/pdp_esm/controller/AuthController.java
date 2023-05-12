package com.example.pdp_esm.controller;

import com.example.pdp_esm.config.JWTUtils;
import com.example.pdp_esm.dto.AuthenticationDTO;
import com.example.pdp_esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
//    private final UserDTO userDTO;
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        final UserDetails user = userRepository.findByEmail(dto.getEmail());

        if (user != null)
            return ResponseEntity.ok(jwtUtils.generateToken(user));

        return ResponseEntity.status(409).body("Some errors occurred!");
    }
}