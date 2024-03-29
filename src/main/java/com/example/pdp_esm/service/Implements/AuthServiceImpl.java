package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.AuthDTO;
import com.example.pdp_esm.dto.RegisterDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.ReserveUsers;
import com.example.pdp_esm.entity.User;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.ReserveUsersRepository;
import com.example.pdp_esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ReserveUsersRepository reserveUsersRepository;

    @Value("${spring.mail.username}")
    String sender;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByPhoneNumber(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "phoneNumber", username));
    }

    public ApiResponse<?> register(RegisterDTO registerDTO) {

        User user = new User();
        user.setEnabled(false);
        user.setFullName(registerDTO.getFullName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());

        final var verifyCode = emailService.verifyCode(user);
        boolean isSuccess = true;
        String message = "Tasdiqlash kodi yuborildi";

        if (verifyCode.isEmpty()) {
            message = "Tasdiqlash kodi yuborishda xatolik yuz berdi";
            isSuccess = false;
        }

        return ApiResponse.builder()
                .message(message)
                .success(isSuccess)
                .build();
    }

    public ApiResponse<?> verify(String email, String otpCode) {

        Optional<User> byEmailAndCode = Optional.ofNullable(userRepository.findByEmailAndVerifyCode(email, otpCode)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email)));

        if (byEmailAndCode.isPresent()) {
            User user = byEmailAndCode.get();
            user.setEnabled(true);
        }

        return ApiResponse.builder()
                .message("Tasdiqlandi")
                .success(true)
                .build();
    }

    public ApiResponse<?> getUserByEmailAndPassword(AuthDTO authDTO) {

        String password = passwordEncoder.encode(authDTO.getPassword());
        String email = authDTO.getEmail();

        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email or password", email)));

        User user = optionalUser.get();
        return ApiResponse.builder()
                .message("User by Email")
                .success(true)
                .data(user)
                .build();
    }

}