package com.gmail.smaglenko.talkandtravel.model.dto;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageDto {
    private Long id;
    private String content;
    private LocalDateTime creationDate = LocalDateTime.now();
    private Country country;
    private User user;
}
