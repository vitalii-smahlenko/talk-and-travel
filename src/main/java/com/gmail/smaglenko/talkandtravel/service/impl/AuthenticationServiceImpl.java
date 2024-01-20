package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.exception.AuthenticationException;
import com.gmail.smaglenko.talkandtravel.exception.RegistrationException;
import com.gmail.smaglenko.talkandtravel.model.Role;
import com.gmail.smaglenko.talkandtravel.model.Token;
import com.gmail.smaglenko.talkandtravel.model.TokenType;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.model.dto.AuthResponse;
import com.gmail.smaglenko.talkandtravel.model.dto.UserDto;
import com.gmail.smaglenko.talkandtravel.service.AuthenticationService;
import com.gmail.smaglenko.talkandtravel.service.JwtService;
import com.gmail.smaglenko.talkandtravel.service.TokenService;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import com.gmail.smaglenko.talkandtravel.util.mapper.UserDtoMapper;
import com.gmail.smaglenko.talkandtravel.util.validator.PasswordValidator;
import com.gmail.smaglenko.talkandtravel.util.validator.UserEmailValidator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordValidator passwordValidator;
    private final UserEmailValidator emailValidator;
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserDtoMapper userDtoMapper;

    @Override
    @Transactional
    public AuthResponse register(User user) {
        isPasswordAndEmailValid(user);
        var existingUser = userService.findUserByEmail(user.getUserEmail());
        isEmailExist(existingUser);
        var jwtToken = jwtService.generateToken(user);
        var savedUser = userService.save(buildUser(user));
        var token = buildToken(jwtToken, savedUser);
        revokeAllUserTokens(savedUser);
        tokenService.save(token);
        return buildAuthResponse(jwtToken, savedUser);
    }

    @Override
    @Transactional
    public AuthResponse login(String userEmail, String password) {
        var userFromDb = userService.findUserByEmail(userEmail);
        isUsernameAndPasswordCorrect(password, userFromDb);
        var existingUser = userFromDb.get();
        var jwtToken = jwtService.generateToken(existingUser);
        var token = buildToken(jwtToken, existingUser);
        revokeAllUserTokens(existingUser);
        tokenService.save(token);
        return buildAuthResponse(jwtToken, existingUser);
    }

    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenService.findAllValidTokensByUserId(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token ->{
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenService.saveAll(validUserTokens);
    }

    private void isUsernameAndPasswordCorrect(String password, Optional<User> userFromDb) {
        if (userFromDb.isEmpty()
                || !passwordEncoder.matches(password, userFromDb.get().getPassword())) {
            throw new AuthenticationException("Incorrect username or password!!!");
        }
    }

    private void isEmailExist(Optional<User> userFromDb) {
        if (userFromDb.isPresent()) {
            throw new RegistrationException("A user with this email already exists");
        }
    }

    private void isPasswordAndEmailValid(User user) {
        if (!emailValidator.isValid(user.getUserEmail())) {
            throw new RegistrationException("Invalid email address");
        }
        if (!passwordValidator.isValid(user.getPassword())) {
            throw new RegistrationException("Passwords must be 8 to 16 characters long and contain "
                    + "at least one letter, one digit, and one special character.");
        }
    }

    private AuthResponse buildAuthResponse(String jwtToken, User user) {
        var userDto = userDtoMapper.mapToDto(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .userDto(userDto)
                .build();
    }

    private Token buildToken(String jwtToken, User savedUser) {
        return Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
    }

    private User buildUser(User user) {
        return User.builder()
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .password(user.getPassword())
                .role(Role.USER)
                .build();
    }

}
