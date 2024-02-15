package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.Participant;
import com.gmail.smaglenko.talkandtravel.repository.CountryRepository;
import com.gmail.smaglenko.talkandtravel.service.CountryService;
import com.gmail.smaglenko.talkandtravel.service.ParticipantService;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repository;
    private final UserService userService;
    private final ParticipantService participantService;

    @Override
    public Country save(Country country) {
        return repository.save(country);
    }

    @Override
    public Country findById(Long countryId) {
        return repository.findById(countryId).orElseThrow(
                () -> new NoSuchElementException("Can not find Country by id " + countryId)
        );
    }

    @Override
    public List<Country> getAll() {
        return repository.findAll().stream()
                .map(this::detachCountryFields)
                .toList();
    }

    @Override
    public Long countUsersInCountry(Long countryId) {
        return repository.countUsersInCountry(countryId);
    }

    @Override
    @Transactional
    public Country createOrUpdateCountryForUser(Country country, Long userID) {
        var user = userService.findById(userID);
        var existingCountry = repository.findByName(country.getName())
                .orElseGet(() -> createNewCountry(country));
        var participant = getParticipant(country,userID);
        existingCountry.getParticipants().add(participant);
        //participant.getCountries().add(existingCountry);
        //joinCountry(existingCountry, participant);
        var savedCountry = save(existingCountry);
        return detachCountryFields(savedCountry);
    }

    @Override
    @Transactional
    public Country create(Country country, Long userId) {
        isCountryExist(country);
        var user = userService.findById(userId);
        var participant = participantService.create(user);
        var newCountry = createNewCountry(country);
        participant.getCountries().add(newCountry);
        newCountry.getParticipants().add(participant);
        var existingCountry = save(newCountry);
        return detachCountryFields(existingCountry);
    }

    @Override
    @Transactional
    public Country update(Long countryId, Long userId) {
        var existingCountry = getCountry(countryId);
        var participant = getParticipant(existingCountry, userId);
        existingCountry.getParticipants().add(participant);
        Country country = repository.save(existingCountry);
        return detachCountryFields(country);
    }

    private Participant getParticipant(Country country, Long userId) {
        var participant = participantService.findByUserIdAndCountryId(userId, country.getId());
        if (participant.isEmpty()) {
            var user = userService.findById(userId);
            return participantService.create(user);
        }
        return participant.get();
    }


    private Country getCountry(Long countryId) {
        return repository.findById(countryId).orElseThrow(
                () -> new NoSuchElementException("The country does not exist yet.")
        );
    }

    private void isCountryExist(Country country) {
        var existingCountry = repository.findByName(country.getName());
        if (existingCountry.isPresent()) {
            throw new RuntimeException("Country already exist");
        }
    }

    private Country detachCountryFields(Country country) {
        return Country.builder()
                .id(country.getId())
                .name(country.getName())
                .flagCode(country.getFlagCode())
                .build();
    }

    private void joinCountry(Country country, Participant participant) {
        country.getParticipants().add(participant);
        participant.getCountries().add(country);
    }

    private Country createNewCountry(Country country) {
        return Country.builder()
                .name(country.getName())
                .flagCode(country.getFlagCode())
                .groupMessages(new ArrayList<>())
                .participants(new HashSet<>())
                .build();
    }
}

