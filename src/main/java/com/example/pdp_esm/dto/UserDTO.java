package com.example.pdp_esm.dto;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDTO {
    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
            new User(

                    "urokovsardor@gamil.com",
                    "password123",
                    Collections.singletonList(new SimpleGrantedAuthority("R0LE_USER"))),
            new User(

                    "urokovsardor70@gamil.com",
                    "password123",
                    Collections.singletonList(new SimpleGrantedAuthority("R0LE_ADMIN")))
    );

    public UserDetails findUserByEmail(String email) {
        return APPLICATION_USERS
                .stream()
                .filter(u -> u.getUsername().equals(email))
                .findFirst().orElseThrow(() -> new UsernameNotFoundException("No user was found"));
    }
}
