package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.User;

public interface AuthenticationService {
    User register(User user);

    User login(String userEmail, String password);
}