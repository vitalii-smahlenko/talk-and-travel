package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.User;
import java.io.IOException;
import java.util.Optional;

public interface UserService {

    User create(User user) throws IOException;

    User update(User user);

    Optional<User> findUserByEmail(String email);

    User findById(Long userId);
}
