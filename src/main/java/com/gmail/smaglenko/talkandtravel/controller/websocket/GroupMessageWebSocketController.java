package com.gmail.smaglenko.talkandtravel.controller.websocket;

import com.gmail.smaglenko.talkandtravel.model.dto.GroupMessageDto;
import com.gmail.smaglenko.talkandtravel.model.dto.GroupMessageRequest;
import com.gmail.smaglenko.talkandtravel.service.GroupMessageService;
import com.gmail.smaglenko.talkandtravel.util.mapper.GroupMessageDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GroupMessageWebSocketController {
    private final GroupMessageService groupMessageService;
    private final GroupMessageDtoMapper groupMessageDtoMapper;

    @MessageMapping("/group-messages/{countryId}/{country-name}")
    @SendTo("/group-message/{country-name}")
    public ResponseEntity<GroupMessageDto> create(@Payload GroupMessageRequest groupMessageRequest) {
        var groupMessage = groupMessageService.create(groupMessageRequest);
        var groupMessageDto = groupMessageDtoMapper.mapToDto(groupMessage);
        return ResponseEntity.ok().body(groupMessageDto);
    }
}
