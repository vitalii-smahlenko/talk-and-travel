package com.gmail.smaglenko.talkandtravel.model.dto;

import com.gmail.smaglenko.talkandtravel.model.Avatar;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String userName;
    private String password;
    private String userEmail;
    private Avatar avatar;
}
