package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.exception.AuthenticationException;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.repository.UserRepository;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) throws IOException {
        String password = user.getPassword();
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        checkDuplicateEmail(user);
        transferSecurityInfoFromExistingUser(user);
        return repository.save(user);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return repository.findByUserEmail(email);
    }

    @Override
    public User findById(Long userId) {
        return repository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("Can not find user by ID: " + userId)
        );
    }

    private void transferSecurityInfoFromExistingUser(User user) {
        var existingUser = findUserById(user);
        user.setPassword(existingUser.getPassword());
        user.setRole(existingUser.getRole());
    }

    private void checkDuplicateEmail(User user) {
        var userByEmail = findUserByEmail(user.getUserEmail());
        if (userByEmail.isPresent()) {
            throw new AuthenticationException("A user with this email already exists");
        }
    }

    private User findUserById(User user) {
        return repository.findById(user.getId())
                .orElseThrow(
                        () -> new NoSuchElementException("Can not find user by ID: " + user.getId())
                );
    }
}
