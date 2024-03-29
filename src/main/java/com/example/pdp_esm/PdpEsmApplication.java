package com.example.pdp_esm;

import com.example.pdp_esm.auth.RegisterRequest;
import com.example.pdp_esm.service.Implements.CertificateDownloader;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import com.example.pdp_esm.auth.AuthenticationService;
import static com.example.pdp_esm.entity.enums.Roles.ADMIN;
import org.springframework.context.annotation.ComponentScan;
import static com.example.pdp_esm.entity.enums.Roles.MANAGER;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.example"})
public class PdpEsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdpEsmApplication.class, args);
    }

        @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .fullName("Admin Adminov")
                    .email("admin@mail.com")
                    .password("password123")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

            var manager = RegisterRequest.builder()
                    .fullName("Manager Managerov")
                    .email("manager@mail.com")
                    .password("password")
                    .role(MANAGER)
                    .build();
            System.out.println("Manager token: " + service.register(manager).getAccessToken());
        };
    }
}