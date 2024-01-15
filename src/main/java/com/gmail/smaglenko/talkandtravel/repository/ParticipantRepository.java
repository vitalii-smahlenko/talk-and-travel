package com.gmail.smaglenko.talkandtravel.repository;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.Participant;
import com.gmail.smaglenko.talkandtravel.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByUser(User user);

    @Query("SELECT NEW Country(c.id, c.name, c.flagCode) "
            + "FROM Participant p "
            + "JOIN p.countries c "
            + "WHERE p.user.id = :userId")
    Optional<List<Country>> findCountriesByUserId(@Param("userId") Long userId);
}
