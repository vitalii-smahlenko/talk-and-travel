package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.model.dto.AuthResponse;

public interface AuthenticationService {
    AuthResponse register(User user);

    AuthResponse login(String userEmail, String password);
}