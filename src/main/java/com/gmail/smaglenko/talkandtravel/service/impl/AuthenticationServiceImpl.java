package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.exception.AuthenticationException;
import com.gmail.smaglenko.talkandtravel.exception.RegistrationException;
import com.gmail.smaglenko.talkandtravel.model.Role;
import com.gmail.smaglenko.talkandtravel.model.Role.RoleName;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.service.AuthenticationService;
import com.gmail.smaglenko.talkandtravel.service.RoleService;
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
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    @Transactional
    public User register(String userName, String userEmail, String password) {
        if (!emailValidator.isValid(userEmail)) {
            throw new RegistrationException("Invalid email address");
        }
        if (!passwordValidator.isValid(password)) {
            throw new RegistrationException("Passwords must be 8 to 16 characters long and contain "
                    + "at least one letter, one digit, and one special character.");
        }
        Optional<User> userFromDb = userService.findUserByEmail(userEmail);
        if (userFromDb.isPresent()) {
            throw new RegistrationException("A user with this email already exists");
        }
        Role role = roleService.findByRoleName(RoleName.USER);
        if (role == null) {
            role = new Role();
            role.setRoleName(RoleName.USER);
            role = roleService.save(role);
        }
        User user = new User();
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setPassword(password);
        user.getRoles().add(role);
        return userService.save(user);
    }

    @Override
    @Transactional
    public User login(String userEmail, String password) {
        Optional<User> userFromDb = userService.findUserByEmail(userEmail);
        if (userFromDb.isEmpty()
                || !passwordEncoder.matches(password, userFromDb.get().getPassword())) {
            throw new AuthenticationException("Incorrect username or password!!!");
        }
        return userService.save(userFromDb.get());
    }
}
