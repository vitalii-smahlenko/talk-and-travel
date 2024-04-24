package com.gmail.smaglenko.talkandtravel.controller.http;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.dto.CountryDto;
import com.gmail.smaglenko.talkandtravel.service.CountryService;
import com.gmail.smaglenko.talkandtravel.util.constants.ApiPathConstants;
import com.gmail.smaglenko.talkandtravel.util.mapper.CountryDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPathConstants.API_BASE_PATH + "/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;
    private final CountryDtoMapper countryDtoMapper;

    @Operation(
            description = "Get all existing countries."
    )
    @GetMapping
    public ResponseEntity<List<CountryDto>> getAll() {
        List<CountryDto> countryDtos = countryService.getAll().stream()
                .map(countryDtoMapper::mapToDto)
                .toList();
        return ResponseEntity.ok().body(countryDtos);
    }

    @Operation(
            description = "Get Country by ID."
    )
    @GetMapping("/{countryId}")
    public ResponseEntity<CountryDto> findById(@PathVariable Long countryId) {
        var country = countryService.findById(countryId);
        var countryDto = countryDtoMapper.mapToDto(country);
        return ResponseEntity.ok().body(countryDto);
    }

    @Operation(
            description = "Get the quantity of participants in the country."
    )
    @GetMapping("/user-count/{countryId}")
    public ResponseEntity<Long> countUsersInCountry(@PathVariable Long countryId) {
        var usersInCountry = countryService.countUsersInCountry(countryId);
        return ResponseEntity.ok().body(usersInCountry);
    }

    @Operation(
            description = "Find all countries where the user is a participant"
    )
    @GetMapping("/all-by-user/{userId}/participating")
    public ResponseEntity<List<CountryDto>> findCountriesByUserId(@PathVariable Long userId) {
        List<Country> countriesByUserId = countryService.findAllCountriesByUser(userId);
        List<CountryDto> responseCountryDtos
                = countriesByUserId.stream()
                .map(countryDtoMapper::mapToDto)
                .toList();
        return ResponseEntity.ok().body(responseCountryDtos);
    }
}
