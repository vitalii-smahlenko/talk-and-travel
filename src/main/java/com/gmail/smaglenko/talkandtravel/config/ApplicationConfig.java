package com.gmail.smaglenko.talkandtravel.config;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Value("${ALLOWED_ORIGIN_1}")
    private String allowedOrigin1;
    @Value("${ALLOWED_ORIGIN_2}")
    private String allowedOrigin2;
    @Value("${ALLOWED_ORIGIN_3}")
    private String allowedOrigin3;
    private final List<String> ALLOWED_ORIGINS
            = Arrays.asList(allowedOrigin1, allowedOrigin2, allowedOrigin3);
    private final List<String> ALLOWED_METHODS
            = Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD");
    private final List<String> ALLOWED_HEADERS
            = Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization");
    private final List<String> EXPOSED_HEADERS
            = Arrays.asList("Content-Type", "Cache-Control", "Content-Language", "Content-Length",
            "Last-Modified");
    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(ALLOWED_ORIGINS);
        cors.setAllowedMethods(ALLOWED_METHODS);
        cors.setAllowedHeaders(ALLOWED_HEADERS);
        cors.setExposedHeaders(EXPOSED_HEADERS);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
}
