package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.exception.AuthenticationException;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.repository.UserRepository;
import com.gmail.smaglenko.talkandtravel.service.UserService;
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
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        Optional<User> userByEmail = findUserByEmail(user.getUserEmail());
        if(userByEmail.isPresent()){
            throw new AuthenticationException("A user with this email already exists");
        }
        User userFromDb = repository.findById(user.getId())
                .orElseThrow(
                        () -> new NoSuchElementException("Can not find user by ID: " + user.getId())
                );
        user.setPassword(userFromDb.getPassword());
        user.setRole(userFromDb.getRole());
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
}
