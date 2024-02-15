package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.Participant;
import com.gmail.smaglenko.talkandtravel.model.User;
import java.util.List;
import java.util.Optional;

public interface ParticipantService {
    Participant save(Participant participant);

    Optional<Participant> findByUserIdAndCountryId(Long userId, Long countryId);

    Participant create(User user);

    List<Country> findAllCountriesByUser(Long userId);
}
