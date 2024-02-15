package com.gmail.smaglenko.talkandtravel.controller;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.dto.CountryDto;
import com.gmail.smaglenko.talkandtravel.service.CountryService;
import com.gmail.smaglenko.talkandtravel.util.constants.ApiPathConstants;
import com.gmail.smaglenko.talkandtravel.util.mapper.CountryDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPathConstants.API_BASE_PATH + "/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;
    private final CountryDtoMapper countryDtoMapper;

    @Operation(
            description = "Get the quantity of participants in the country."
    )
    @GetMapping("/user-count/{countryId}")
    public ResponseEntity<Long> countUsersInCountry(@PathVariable Long countryId) {
        var usersInCountry = countryService.countUsersInCountry(countryId);
        return ResponseEntity.ok().body(usersInCountry);
    }

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
            description = "Create new country."
    )
    @PostMapping
    public ResponseEntity<CountryDto> create(@RequestBody CountryDto countryDto) {
        var country = countryDtoMapper.mapToModel(countryDto);
        var newCountry = countryService.create(country, countryDto.getUserId());
        var dto = countryDtoMapper.mapToDto(newCountry);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(
            description = "Update existing country."
    )
    @PutMapping
    public ResponseEntity<CountryDto> update(@RequestBody CountryDto countryDto) {
        Country country = countryService.update(countryDto.getId(), countryDto.getUserId());
        var dto = countryDtoMapper.mapToDto(country);
        return ResponseEntity.ok().body(dto);
    }

    /*@MessageMapping("/country")
    @SendTo("/topic/country")*/
    @PostMapping("/create-or-update")
    public CountryDto createOrUpdateCountryForUser(@RequestBody CountryDto countryDto) {
        var requestedCountry = countryDtoMapper.mapToModel(countryDto);
        var country
                = countryService
                .createOrUpdateCountryForUser(requestedCountry, countryDto.getUserId());
        return countryDtoMapper.mapToDto(country);
    }
}
