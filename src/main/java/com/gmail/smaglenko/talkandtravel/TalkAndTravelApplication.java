package com.gmail.smaglenko.talkandtravel;

import com.gmail.smaglenko.talkandtravel.model.Role;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.gmail.smaglenko")
@OpenAPIDefinition(
        info = @Info(
                title = "Talk&Travel project",
                version = "0.0.2",
                description = "This is a simple chat application for people who like to travel, "
                        + "written in Java using Spring Boot."
        )
)
@RequiredArgsConstructor
public class TalkAndTravelApplication {
    @Value("${USER_ADMIN_NAME}")
    private String adminName;
    @Value("${USER_ADMIN_EMAIL}")
    private String adminEmail;
    @Value("${USER_ADMIN_PASSWORD}")
    private String adminPassword;
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(TalkAndTravelApplication.class, args);
    }

    @Bean
    CommandLineRunner run() {
        return args -> {
            Optional<User> userByEmail = userService.findUserByEmail(adminEmail);
            if (userByEmail.isEmpty()) {
                userService.save(User.builder()
                        .userName(adminName)
                        .userEmail(adminEmail)
                        .password(adminPassword)
                        .role(Role.ADMIN)
                        .build());
            }
        };
    }
}
