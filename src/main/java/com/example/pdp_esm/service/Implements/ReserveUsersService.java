package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.ReserveUserDTO;
import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.ReserveUsers;
import com.example.pdp_esm.entity.User;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.ReserveUsersRepository;
import com.example.pdp_esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReserveUsersService {

    private final ReserveUsersRepository reserveUsersRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<?> updateUser(ReserveUserDTO reserveUserDTO) {

        Optional<User> optionalUser = Optional.ofNullable(userRepository.findUserByPhoneNumber(reserveUserDTO.getPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException("User", "phoneNumber", reserveUserDTO.getPhoneNumber())));

        User user = optionalUser.get();
        user.setEmail(reserveUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(reserveUserDTO.getPassword()));

        return ApiResponse.builder()
                .message("User updated")
                .success(true)
                .data(userRepository.save(user))
                .build();
    }

    public String returnOTP(User user) {

        final String usersDType = userRepository.getUsersDType(user.getPhoneNumber()); //users dType maybe Student or Teacher
        String otp = "";
        String rndCode = UUID.randomUUID().toString().substring(0, 5); //length 5
        ReserveUsers reserveUser = new ReserveUsers();

        if (usersDType.equals("Student"))
            otp = "AS-" + rndCode;
        else if (usersDType.equals("Teacher"))
            otp = "AT-" + rndCode;

        reserveUser.setDType(usersDType);
        reserveUser.setPhoneNumber(user.getPhoneNumber());
        reserveUser.setOtpCode(otp);
        reserveUser.setFullName(user.getFullName());
        reserveUsersRepository.save(reserveUser);
        return otp;
    }
}