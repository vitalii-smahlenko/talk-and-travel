package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.User;

public interface AuthenticationService {
    User register(String userName, String email, String password);

    User login(String userEmail, String password);
}