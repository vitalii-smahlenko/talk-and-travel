package com.gmail.smaglenko.talkandtravel.util.mapper;

import com.gmail.smaglenko.talkandtravel.model.GroupMessage;
import com.gmail.smaglenko.talkandtravel.model.dto.GroupMessageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMessageDtoMapper {
    GroupMessageDto mapToDto(GroupMessage groupMessage);

    GroupMessage mapToModel(GroupMessageDto dto);
}
