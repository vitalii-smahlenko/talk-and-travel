package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.model.dto.AuthResponse;
import java.io.IOException;

public interface AuthenticationService {
    AuthResponse register(User user) throws IOException;

    AuthResponse login(User user);
}