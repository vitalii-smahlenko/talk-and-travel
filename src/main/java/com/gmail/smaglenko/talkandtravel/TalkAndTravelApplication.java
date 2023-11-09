package com.gmail.smaglenko.talkandtravel;

import com.gmail.smaglenko.talkandtravel.model.Role;
import com.gmail.smaglenko.talkandtravel.model.Role.RoleName;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.service.RoleService;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

    /*@Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            Role role = roleService.findByRoleName(RoleName.ADMIN);
            if (role == null) {
                role = new Role();
                role.setRoleName(RoleName.ADMIN);
                role = roleService.save(role);
            }
            User admin = new User();
            admin.setUserName("admin");
            admin.setUserEmail("admin@t2.com");
            admin.setPassword("admin");
            admin.getRoles().add(role);
            userService.save(admin);
        };
    }*/

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("http://localhost:3001", "http://localhost:3000"));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS","HEAD"));
        cors.setAllowedHeaders(List.of("X-Requested-With", "Origin", "Content-Type", "Accept",
                "Authorization"));
        cors.setExposedHeaders(List.of("Content-Type", "Cache-Control", "Content-Language",
                "Content-Length", "Last-Modified"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
}
