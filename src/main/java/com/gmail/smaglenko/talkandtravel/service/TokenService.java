package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.Token;
import java.util.List;
import java.util.Optional;

public interface TokenService {
    Token save(Token token);

    List<Token> findAllValidTokensByUserId(Long userId);

    Optional<Token> findByToken(String token);

    List<Token> saveAll(List<Token> tokens);

    void deleteInvalidTokensByUserId(Long userId);
}
