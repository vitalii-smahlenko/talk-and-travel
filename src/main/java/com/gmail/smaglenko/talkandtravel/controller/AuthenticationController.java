package com.gmail.smaglenko.talkandtravel.controller;

import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.model.dto.AuthDto;
import com.gmail.smaglenko.talkandtravel.model.dto.UserDto;
import com.gmail.smaglenko.talkandtravel.service.AuthenticationService;
import com.gmail.smaglenko.talkandtravel.service.JwtService;
import com.gmail.smaglenko.talkandtravel.util.mapper.UserDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final UserDtoMapper mapper;
    private final JwtService jwtService;

    @Operation(
            description = "Register a user."
    )
    @PostMapping("/register")
    public ResponseEntity<AuthDto> register(@RequestBody UserDto dto) {
        User user = authService.register(mapper.mapToModel(dto));
        AuthDto authDto = createAuthDto(user);
        return ResponseEntity.ok(authDto);
    }

    @Operation(
            description = "Log in a user."
    )
    @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody UserDto dto) {
        User user = authService.login(dto.getUserEmail(), dto.getPassword());
        AuthDto authDto = createAuthDto(user);
        return ResponseEntity.ok(authDto);
    }

    @Operation(
            description = "Logout the current user."
    )
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return ResponseEntity.noContent().build();
    }

    private AuthDto createAuthDto(User user) {
        String token = jwtService.generateToken(user);
        UserDto userDto = mapper.mapToDto(user);
        return AuthDto.builder()
                .token(token)
                .userDto(userDto)
                .build();
    }
}
