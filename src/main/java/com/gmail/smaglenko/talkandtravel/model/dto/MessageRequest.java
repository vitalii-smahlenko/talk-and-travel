package com.gmail.smaglenko.talkandtravel.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequest {
    private String content;
    private Long senderId;
    private Long countryId;
}
