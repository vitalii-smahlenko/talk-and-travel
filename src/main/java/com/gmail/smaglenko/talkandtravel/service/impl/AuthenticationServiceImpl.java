package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.exception.AuthenticationException;
import com.gmail.smaglenko.talkandtravel.exception.RegistrationException;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.service.AuthenticationService;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final Integer MIN_PASSWORD_LENGTH = 8;
    private final Integer MAX_PASSWORD_LENGTH = 16;
    private final String VALID_EMAIL_REGEX = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(String userName, String userEmail, String password) {
        if (!isValidEmailAddress(userEmail)) {
            throw new RegistrationException("Invalid email address");
        }
        if (!isValidPassword(password)) {
            throw new RegistrationException("Passwords must be 8 to 16 characters long and contain "
                    + "at least one letter, one digit, and one special character.");
        }
        Optional<User> userFromDb = userService.findUserByEmail(userEmail);
        if (userFromDb.isPresent()) {
            throw new RegistrationException("A user with this email already exists");
        }
        User user = new User();
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setPassword(password);
        return userService.save(user);
    }

    @Override
    public User login(String userEmail, String password) {
        Optional<User> userFromDb = userService.findUserByEmail(userEmail);
        if (userFromDb.isEmpty()
                || !passwordEncoder.matches(password, userFromDb.get().getPassword())) {
            throw new AuthenticationException("Incorrect username or password!!!");
        }
        return userFromDb.get();
    }

    private boolean isValidPassword(String password) {
        if ((password.length() <= MIN_PASSWORD_LENGTH)
                || (password.length() >= MAX_PASSWORD_LENGTH)) {
            return false;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialCharacter = true;
            }
        }
        return hasLetter && hasDigit && hasSpecialCharacter;
    }

    private boolean isValidEmailAddress(String email) {
        Pattern pattern = Pattern.compile(VALID_EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
