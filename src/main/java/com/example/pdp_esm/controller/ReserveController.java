package com.example.pdp_esm.controller;

import com.example.pdp_esm.dto.ReserveUserDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.service.Implements.ReserveUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reserve")
@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN', 'MANAGER')")
public class
ReserveController {

    private final ReserveUsersService reserveUsersService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ReserveUserDTO dto) {
        final ApiResponse<?> response = reserveUsersService.updateUser(dto);

        if (response.isSuccess()) log.warn("User Updated! -> {}", response.getData());
        else log.error(response.getMessage());

        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }
}