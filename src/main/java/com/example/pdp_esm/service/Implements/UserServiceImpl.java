package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.RegisterDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.User;
import com.example.pdp_esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl{

    @Value("${spring.mail.username}")
    String sender;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByPhone(username).orElse(null);
//    }

    public ApiResponse<?> register(RegisterDTO registerDTO) {
        User user = new User();
//        user.(false); //security qo'shilganda
        user.setFullName(registerDTO.getFullName());
        user.setPassword(registerDTO.getPassword());
        user.setEmail(registerDTO.getEmail());

        //6-xonali email tasdiqlash kodi jo'natiladi
        String code = String.valueOf(Math.random() * 899999 + 100000);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Tasdiqlash kodi : " + code);
        simpleMailMessage.setText("Salom");
        javaMailSender.send(simpleMailMessage);
        userRepository.save(user);

        return ApiResponse.builder()
                .message("Tasdiqlash kodi yuborildi")
                .success(true)
                .build();
    }

//        public ApiResponse<?> verify(String email, String code) {
//            Optional<User> byEmailAndCode = userRepository.findByEmailAndCode(email, code);
//
//            if (byEmailAndCode.isPresent()) {
//                User user = byEmailAndCode.get();
//                user.setEnabled(true);
//            }
//            return ApiResponse.builder().message("Tasdiqlandi").success(true).build();
//    }
}