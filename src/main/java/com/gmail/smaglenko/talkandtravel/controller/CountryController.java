package com.gmail.smaglenko.talkandtravel.controller;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.dto.CountryDto;
import com.gmail.smaglenko.talkandtravel.service.CountryService;
import com.gmail.smaglenko.talkandtravel.util.mapper.CountryDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/country")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;
    private final CountryDtoMapper dtoMapper;

    @Operation(
            description = "This method creates a Country or returns an existing one "
                    + "and adds a User to it"
    )
    @PostMapping("/{userID}")
    public ResponseEntity<CountryDto> createOrUpdateCountryForUser(
            @RequestBody CountryDto countryDto, @PathVariable Long userID) {
        Country model = dtoMapper.mapToModel(countryDto);
        Country country = countryService.createOrUpdateCountryForUser(model, userID);
        CountryDto dto = dtoMapper.mapToDto(country);
        return ResponseEntity.ok().body(dto);
    }
}
