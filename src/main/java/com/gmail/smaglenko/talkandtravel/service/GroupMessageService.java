package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.GroupMessage;
import com.gmail.smaglenko.talkandtravel.model.dto.GroupMessageRequest;
import java.util.List;

public interface GroupMessageService {
    GroupMessage save(GroupMessage groupMessage);

    GroupMessage create(GroupMessageRequest groupMessageRequest);

    List<GroupMessage> findByCountryIdOrderByCreationDateDesc(Long countryId);
}
