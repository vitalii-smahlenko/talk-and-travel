package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.Country;
import java.util.List;

public interface CountryService {
    Country createOrUpdateCountryForUser(Country country, Long userID);

    Country create(Country country, Long userID);

    Country update(Long countryId, Long userID);

    Country save(Country country);

    Country findById(Long countryId);

    List<Country> getAll();

    Long countUsersInCountry(Long countryId);
}
