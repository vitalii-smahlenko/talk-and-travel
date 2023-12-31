package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.User;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);

    String generateToken(User user);

    String generateToken(Map<String, Object> extraClaims, User user);

    Boolean isTokenValid(String token, UserDetails userDetails);
}
