package com.gmail.smaglenko.talkandtravel.controller;

import com.gmail.smaglenko.talkandtravel.model.Message;
import com.gmail.smaglenko.talkandtravel.model.dto.MessageDto;
import com.gmail.smaglenko.talkandtravel.model.dto.MessageRequest;
import com.gmail.smaglenko.talkandtravel.service.MessageService;
import com.gmail.smaglenko.talkandtravel.util.constants.ApiPathConstants;
import com.gmail.smaglenko.talkandtravel.util.mapper.MessageDtoMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPathConstants.API_BASE_PATH + "/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageDtoMapper messageDtoMapper;

    @GetMapping("/{countryId}")
    public ResponseEntity<List<MessageDto>> findByCountryIdOrderByCreationDateDesc(
            @PathVariable Long countryId) {
        List<Message> messagesByCountryIdOrderByCreationDateDesc
                = messageService.findByCountryIdOrderByCreationDateDesc(countryId);
        List<MessageDto> messageDtos
                = messagesByCountryIdOrderByCreationDateDesc.stream()
                .map(messageDtoMapper::mapToDto)
                .toList();
        return ResponseEntity.ok().body(messageDtos);
    }

    @PostMapping()
    public ResponseEntity<MessageDto> create(@RequestBody MessageRequest messageRequest) {
        Message message = messageService.create(messageRequest);
        MessageDto messageDto = messageDtoMapper.mapToDto(message);
        return ResponseEntity.ok().body(messageDto);
    }
}
