package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.Message;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.model.dto.MessageRequest;
import com.gmail.smaglenko.talkandtravel.repository.MessageRepository;
import com.gmail.smaglenko.talkandtravel.service.CountryService;
import com.gmail.smaglenko.talkandtravel.service.MessageService;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;
    private final UserService userService;
    private final CountryService countryService;

    @Override
    public Message save(Message message) {
        return repository.save(message);
    }

    @Override
    @Transactional
    public Message create(MessageRequest messageRequest) {
        var existingUser = userService.findById(messageRequest.getSenderId());
        var existingCountry = countryService.findById(messageRequest.getCountryId());
        var message = buildMessage(messageRequest.getContent(), existingUser, existingCountry);
        var savedMessage = save(message);
        existingCountry.getMessages().add(savedMessage);
        countryService.save(existingCountry);
        return detachMessageFields(savedMessage);
    }

    @Override
    public List<Message> findByCountryIdOrderByCreationDateDesc(Long countryId) {
        List<Message> messagesByCountryIdOrderByCreationDateDesc
                = repository.findByCountryIdOrderByCreationDateDesc(countryId);
        return messagesByCountryIdOrderByCreationDateDesc.stream()
                .map(this::detachMessageFields)
                .toList();
    }

    private Message detachMessageFields(Message message) {
        return Message.builder()
                .id(message.getId())
                .content(message.getContent())
                .creationDate(message.getCreationDate())
                .build();
    }

    private Message buildMessage(String content, User userFromDb, Country countryFromDb) {
        return Message.builder()
                .content(content)
                .user(userFromDb)
                .country(countryFromDb)
                .build();
    }
}
