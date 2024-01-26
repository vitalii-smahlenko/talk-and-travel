package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.exception.AuthenticationException;
import com.gmail.smaglenko.talkandtravel.model.Avatar;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.repository.UserRepository;
import com.gmail.smaglenko.talkandtravel.service.AvatarService;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        var userByEmail = findUserByEmail(user.getUserEmail());
        isEmailAlreadyExist(userByEmail);
        var existingUser = getExistingUser(user);
        user.setPassword(existingUser.getPassword());
        user.setRole(existingUser.getRole());
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

    private void isEmailAlreadyExist(Optional<User> userByEmail) {
        if (userByEmail.isPresent()) {
            throw new AuthenticationException("A user with this email already exists");
        }
    }

    private User getExistingUser(User user) {
        return repository.findById(user.getId())
                .orElseThrow(
                        () -> new NoSuchElementException("Can not find user by ID: " + user.getId())
                );
    }
}
