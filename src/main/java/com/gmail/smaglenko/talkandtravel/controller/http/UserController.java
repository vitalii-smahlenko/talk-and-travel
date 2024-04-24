package com.gmail.smaglenko.talkandtravel.controller.http;

import com.gmail.smaglenko.talkandtravel.model.dto.UserDto;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import com.gmail.smaglenko.talkandtravel.util.constants.ApiPathConstants;
import com.gmail.smaglenko.talkandtravel.util.mapper.UserDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPathConstants.API_BASE_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @Operation(
            description = "Update a user."
    )
    @PutMapping()
    public ResponseEntity<UserDto> update(@RequestBody UserDto dto) {
        var user = userDtoMapper.mapToModel(dto);
        var updatedUser = userService.update(user);
        var userDto = userDtoMapper.mapToDto(updatedUser);
        return ResponseEntity.ok().body(userDto);
    }

    @Operation(
            description = "Get a user by ID."
    )
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable Long userId) {
        var user = userService.findById(userId);
        var userDto = userDtoMapper.mapToDto(user);
        return ResponseEntity.ok().body(userDto);
    }
}
