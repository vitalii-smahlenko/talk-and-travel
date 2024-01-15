package com.gmail.smaglenko.talkandtravel.util.mapper;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.dto.CountryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryDtoMapper {
    CountryDto mapToDto(Country country);

    Country mapToModel(CountryDto dto);
}
