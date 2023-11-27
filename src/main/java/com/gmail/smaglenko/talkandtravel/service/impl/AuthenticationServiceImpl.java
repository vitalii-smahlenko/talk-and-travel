package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.exception.AuthenticationException;
import com.gmail.smaglenko.talkandtravel.exception.RegistrationException;
import com.gmail.smaglenko.talkandtravel.model.Avatar;
import com.gmail.smaglenko.talkandtravel.model.Role;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.service.AuthenticationService;
import com.gmail.smaglenko.talkandtravel.service.AvatarService;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import com.gmail.smaglenko.talkandtravel.util.validator.PasswordValidator;
import com.gmail.smaglenko.talkandtravel.util.validator.UserEmailValidator;
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
    private final AvatarService avatarService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User register(User user) {
        if (!emailValidator.isValid(user.getUserEmail())) {
            throw new RegistrationException("Invalid email address");
        }
        if (!passwordValidator.isValid(user.getPassword())) {
            throw new RegistrationException("Passwords must be 8 to 16 characters long and contain "
                    + "at least one letter, one digit, and one special character.");
        }
        Optional<User> userFromDb = userService.findUserByEmail(user.getUserEmail());
        if (userFromDb.isPresent()) {
            throw new RegistrationException("A user with this email already exists");
        }
        Avatar avatar = avatarService.save(Avatar.builder()
                .content(new byte[]{(byte) user.getUserName().charAt(0)})
                .build());
        return userService.save(User.builder()
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .password(user.getPassword())
                .role(Role.USER)
                .avatar(avatar)
                .build());
    }

    @Override
    @Transactional
    public User login(String userEmail, String password) {
        Optional<User> userFromDb = userService.findUserByEmail(userEmail);
        if (userFromDb.isEmpty()
                || !passwordEncoder.matches(password, userFromDb.get().getPassword())) {
            throw new AuthenticationException("Incorrect username or password!!!");
        }
        return userFromDb.get();
    }
}
