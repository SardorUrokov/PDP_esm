package com.example.pdp_esm.controller;

import com.example.pdp_esm.config.JWTUtils;
import com.example.pdp_esm.dto.AuthDTO;
import com.example.pdp_esm.dto.RegisterDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.repository.UserRepository;
import com.example.pdp_esm.service.Implements.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthServiceImpl authService;
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        final UserDetails user = userRepository.findByEmail(dto.getEmail());

        if (user != null)
            return ResponseEntity.ok(jwtUtils.generateToken(user));

        return ResponseEntity.status(409).body("Some errors occurred!");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDto) {
        ApiResponse<?> response = authService.register(registerDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("email") String email, @RequestParam("code") Integer otpCode) {
        ApiResponse<?> response = authService.verify(email, otpCode);
        return ResponseEntity.ok(response);
    }
}