package com.gmail.smaglenko.talkandtravel.repository;

import com.gmail.smaglenko.talkandtravel.model.Country;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String name);

    @Query("SELECT COUNT (DISTINCT p.user.id) "
            + "FROM  Participant p "
            + "JOIN p.countries c "
            + "WHERE c.id = :countryId")
    Long countUsersInCountry(Long countryId);
}
