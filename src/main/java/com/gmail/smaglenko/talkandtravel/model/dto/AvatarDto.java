package com.gmail.smaglenko.talkandtravel.model.dto;

import com.gmail.smaglenko.talkandtravel.model.User;
import lombok.Data;

@Data
public class AvatarDto {
    private Long id;
    private User user;
    private byte[] content;
}
