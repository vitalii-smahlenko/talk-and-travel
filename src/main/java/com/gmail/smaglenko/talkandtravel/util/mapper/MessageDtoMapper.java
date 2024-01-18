package com.gmail.smaglenko.talkandtravel.util.mapper;

import com.gmail.smaglenko.talkandtravel.model.Message;
import com.gmail.smaglenko.talkandtravel.model.dto.MessageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageDtoMapper {
    MessageDto mapToDto(Message message);

    Message mapToModel(MessageDto dto);
}
