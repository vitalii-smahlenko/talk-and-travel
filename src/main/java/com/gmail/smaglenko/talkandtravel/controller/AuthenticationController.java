package com.gmail.smaglenko.talkandtravel.controller;

import com.gmail.smaglenko.talkandtravel.model.dto.UserDto;
import com.gmail.smaglenko.talkandtravel.service.AuthenticationService;
import com.gmail.smaglenko.talkandtravel.util.mapper.UserDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
//@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authService;
    private final UserDtoMapper mapper;

    @Operation(
            description = "Register a user."
    )
    @PostMapping("/register")
    public UserDto register(@RequestBody UserDto dto) {
        return mapper.mapToDto(authService
                .register(dto.getUserName(), dto.getUserEmail(), dto.getPassword()));
    }

    @Operation(
            description = "Log in a user."
    )
    @PostMapping("/login")
    public UserDto login(@RequestBody UserDto dto) {
        return mapper.mapToDto(authService.login(dto.getUserEmail(), dto.getPassword()));
    }
}
