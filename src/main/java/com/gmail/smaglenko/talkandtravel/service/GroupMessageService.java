package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.GroupMessage;
import com.gmail.smaglenko.talkandtravel.model.dto.GroupMessageRequestDto;
import java.util.List;

public interface GroupMessageService {
    GroupMessage save(GroupMessage groupMessage);

    GroupMessage create(GroupMessageRequestDto groupMessageRequestDto);

    List<GroupMessage> findByCountryIdOrderByCreationDateDesc(Long countryId);
}
