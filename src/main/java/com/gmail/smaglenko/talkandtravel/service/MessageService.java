package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.Message;
import com.gmail.smaglenko.talkandtravel.model.dto.MessageRequest;
import java.util.List;

public interface MessageService {
    Message save(Message message);

    Message create(MessageRequest messageRequest);

    List<Message> findByCountryIdOrderByCreationDateDesc(Long countryId);
}
