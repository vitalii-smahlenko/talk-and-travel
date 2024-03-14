package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Avatar;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.model.dto.AuthResponse;
import com.gmail.smaglenko.talkandtravel.model.dto.UserDto;
import com.gmail.smaglenko.talkandtravel.service.AvatarService;
import com.gmail.smaglenko.talkandtravel.service.JwtService;
import com.gmail.smaglenko.talkandtravel.service.TokenService;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import com.gmail.smaglenko.talkandtravel.util.mapper.UserDtoMapper;
import com.gmail.smaglenko.talkandtravel.util.validator.PasswordValidator;
import com.gmail.smaglenko.talkandtravel.util.validator.UserEmailValidator;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    private static final String USER_PASSWORD = "!123456Aa";
    private static final String USER_NAME = "Bob";
    private static final String USER_EMAIL = "bob@mail.com";
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    private static final Long USER_ID = 1L;
    @Mock
    private PasswordValidator passwordValidator;
    @Mock
    private UserEmailValidator emailValidator;
    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;
    @Mock
    private UserDtoMapper userDtoMapper;
    @Mock
    private AvatarService avatarService;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = creanteNewUserDto();
        user = createNewUser();
    }

    @Test
    void register_shouldSaveUserWithCorrectData() throws IOException {
        when(userService.save(any())).thenReturn(user);
        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(Optional.empty());
        when(emailValidator.isValid(USER_EMAIL)).thenReturn(true);
        when(passwordValidator.isValid(USER_PASSWORD)).thenReturn(true);
        when(avatarService.createDefaultAvatar(USER_NAME)).thenReturn(new Avatar());
        when(avatarService.save(any())).thenReturn(new Avatar());
        when(jwtService.generateToken(user)).thenReturn("test_token");
        when(tokenService.save(any())).thenReturn(null);
        when(userDtoMapper.mapToDto(user)).thenReturn(userDto);

        User expected = user;

        AuthResponse authResponse = authenticationService.register(user);
        UserDto actual = authResponse.getUserDto();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUserEmail(), actual.getUserEmail());
        assertEquals(expected.getUserName(), actual.getUserName());
        assertEquals(expected.getPassword(), actual.getPassword());

        verify(userService, times(1)).save(any());
        verify(userService, times(1)).findUserByEmail(USER_EMAIL);
        verify(emailValidator, times(1)).isValid(USER_EMAIL);
        verify(passwordValidator, times(1)).isValid(USER_PASSWORD);
        verify(avatarService, times(1)).createDefaultAvatar(USER_NAME);
        verify(avatarService, times(1)).save(any());
        verify(jwtService, times(1)).generateToken(user);
        verify(tokenService, times(1)).save(any());
        verify(userDtoMapper, times(1)).mapToDto(user);
    }



    private User createNewUser() {
        return User.builder()
                .id(USER_ID)
                .password(USER_PASSWORD)
                .userName(USER_NAME)
                .userEmail(USER_EMAIL)
                .build();
    }

    private UserDto creanteNewUserDto() {
        return UserDto.builder()
                .id(USER_ID)
                .userEmail(USER_EMAIL)
                .password(USER_PASSWORD)
                .userName(USER_NAME)
                .build();
    }

}