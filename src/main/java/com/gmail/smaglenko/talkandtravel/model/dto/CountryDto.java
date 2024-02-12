package com.gmail.smaglenko.talkandtravel.model.dto;

import com.gmail.smaglenko.talkandtravel.model.GroupMessage;
import com.gmail.smaglenko.talkandtravel.model.Participant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
    private Long id;
    private String name;
    private String flagCode;
    private Long userId;
    private List<GroupMessage> groupMessages;
    private List<Participant> participants;
}
