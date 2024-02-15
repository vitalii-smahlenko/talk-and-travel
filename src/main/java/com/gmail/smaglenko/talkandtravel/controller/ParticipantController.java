package com.gmail.smaglenko.talkandtravel.controller;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.dto.CountryDto;
import com.gmail.smaglenko.talkandtravel.service.ParticipantService;
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
@RequestMapping(ApiPathConstants.API_BASE_PATH + "/participants")
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;
    private final CountryDtoMapper countryDtoMapper;

    @Operation(
            description = "Find all countries where the user is a participant"
    )
    @GetMapping("/countries/userId/{userId}")
    public ResponseEntity<List<CountryDto>> findCountriesByUserId(@PathVariable Long userId) {
        List<Country> countriesByUserId = participantService.findAllCountriesByUser(userId);
        List<CountryDto> responseCountryDtos
                = countriesByUserId.stream()
                .map(countryDtoMapper::mapToDto)
                .toList();
        return ResponseEntity.ok().body(responseCountryDtos);
    }
}
