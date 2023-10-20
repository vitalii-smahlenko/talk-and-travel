package com.gmail.smaglenko.talkandtravel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.gmail.smaglenko")
@OpenAPIDefinition(
        info = @Info(
                title = "Talk&Travel project",
                version = "0.0.1",
                description = "This is a simple chat application for people who like to travel, "
                        + "written in Java using Spring Boot."
        )
)
public class TalkAndTravelApplication {
    public static void main(String[] args) {
        SpringApplication.run(TalkAndTravelApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
