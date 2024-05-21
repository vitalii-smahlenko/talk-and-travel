package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.Country;
import java.util.List;

public interface CountryService {

    Country create(Country country, Long userID);

    Country update(Long countryId, Long userID);

    Country save(Country country);

    Country findById(Long countryId);

    Country findByName(String countryMame);

    List<Country> getAll();

    Long countUsersInCountry(Long countryId);

    List<Country> findAllCountriesByUser(Long userId);
}
