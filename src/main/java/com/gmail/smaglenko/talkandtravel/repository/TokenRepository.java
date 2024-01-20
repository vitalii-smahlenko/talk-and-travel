package com.gmail.smaglenko.talkandtravel.repository;

import com.gmail.smaglenko.talkandtravel.model.Token;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t "
            + "from Token t "
            + "JOIN User u "
            + "ON t.user.id = u.id "
            + "WHERE u.id =:userId "
            + "AND (t.expired = false OR t.revoked = false )"
    )
    List<Token> findAllValidTokensByUserId(Long userId);

    Optional<Token> findByToken(String token);
}
