package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.User;
import java.util.Optional;

public interface UserService {

    User save(User user);

    User update(User user);

    Optional<User> findUserByEmail(String email);

    User findById(Long userId);
}
