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
    public Country findByName(String countryMame) {
        return repository.findByName(countryMame).orElseThrow(
                () -> new NoSuchElementException("Can not find Country by mane " + countryMame)
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
    public List<Country> findAllCountriesByUser(Long userId) {
        return repository.findCountriesByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("The User is not a participant of any Country")
        );
    }

    @Override
    @Transactional
    public Country create(Country country, Long userId) {
        isCountryExist(country);
        var user = userService.findById(userId);
        var participant = participantService.create(user);
        var newCountry = createNewCountry(country);
        joinCountry(newCountry, participant);
        var savedCountry = save(newCountry);
        return detachCountryFields(savedCountry);
    }

    @Override
    @Transactional
    public Country update(Long countryId, Long userId) {
        var country = getCountry(countryId);
        var participant = getParticipant(countryId, userId);
        joinCountry(country, participant);
        var savedCountry = repository.save(country);
        return detachCountryFields(savedCountry);
    }

    private Participant getParticipant(Long countryId, Long userId) {
        var user = userService.findById(userId);
        return participantService.findByUserIdAndCountryId(userId, countryId)
                .orElseGet(() -> participantService.create(user));
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
        if (!country.getParticipants().contains(participant)) {
            country.getParticipants().add(participant);
        }
        if (!participant.getCountries().contains(country)) {
            participant.getCountries().add(country);
        }
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
