package com.gmail.smaglenko.talkandtravel.model.dto;

import com.gmail.smaglenko.talkandtravel.model.Avatar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String userEmail;
    private String password;
    private Avatar avatar;
}
