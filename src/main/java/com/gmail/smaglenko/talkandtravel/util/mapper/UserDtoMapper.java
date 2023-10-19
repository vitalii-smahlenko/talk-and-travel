package com.gmail.smaglenko.talkandtravel.util.mapper;

import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(target = "password", ignore = true)
    UserDto mapToDto(User user);

    @Mapping(target = "password", ignore = true)
    User mapToModel(UserDto dto);
}
