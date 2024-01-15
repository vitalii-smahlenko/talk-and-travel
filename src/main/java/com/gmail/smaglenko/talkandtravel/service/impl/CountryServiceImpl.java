package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.Participant;
import com.gmail.smaglenko.talkandtravel.repository.CountryRepository;
import com.gmail.smaglenko.talkandtravel.service.CountryService;
import com.gmail.smaglenko.talkandtravel.service.ParticipantService;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import java.util.ArrayList;
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
    public Country findByName(String name) {
        return repository.findByName(name).orElseThrow(
                () -> new NoSuchElementException("Can not find country " + name)
        );
    }

    @Override
    public Country findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Can not find country by ID: " + id)
        );
    }

    @Override
    @Transactional
    public Country createOrUpdateCountryForUser(Country country, Long userID) {
        var user = userService.findById(userID);
        var existingCountry = repository.findByName(country.getName())
                .orElseGet(() -> create(country));
        var existingParticipant = participantService.findByUser(user)
                .orElseGet(() -> participantService.create(user));
        joinCountry(existingCountry, existingParticipant);
        Country savedCountry = save(existingCountry);
        return detachFields(savedCountry);
    }

    private Country detachFields(Country country) {
        return Country.builder()
                .id(country.getId())
                .name(country.getName())
                .flagCode(country.getFlagCode())
                .build();
    }

    private void joinCountry(Country country, Participant participant) {
        if (!country.getParticipants().contains(participant)) {
            country.getParticipants().add(participant);
        }
        if (!participant.getCountries().contains(country)) {
            participant.getCountries().add(country);
        }
    }

    private Country create(Country country) {
        return Country.builder()
                .name(country.getName())
                .flagCode(country.getFlagCode())
                .messages(new ArrayList<>())
                .participants(new ArrayList<>())
                .build();
    }
}

