package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.GroupMessage;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.model.dto.GroupMessageRequest;
import com.gmail.smaglenko.talkandtravel.repository.GroupMessageRepository;
import com.gmail.smaglenko.talkandtravel.service.CountryService;
import com.gmail.smaglenko.talkandtravel.service.GroupMessageService;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupMessageServiceImpl implements GroupMessageService {
    private final GroupMessageRepository repository;
    private final UserService userService;
    private final CountryService countryService;

    @Override
    public GroupMessage save(GroupMessage groupMessage) {
        return repository.save(groupMessage);
    }

    @Override
    @Transactional
    public GroupMessage create(GroupMessageRequest groupMessageRequest) {
        var existingUser = userService.findById(groupMessageRequest.getSenderId());
        var existingCountry = countryService.findById(groupMessageRequest.getCountryId());
        var groupMessage = buildGroupMessage(groupMessageRequest.getContent(), existingUser, existingCountry);
        var savedGroupMessage = save(groupMessage);
        existingCountry.getGroupMessages().add(savedGroupMessage);
        countryService.save(existingCountry);
        return detachGroupMessageFields(savedGroupMessage);
    }

    @Override
    public List<GroupMessage> findByCountryIdOrderByCreationDateDesc(Long countryId) {
        var groupMessagesByCountryIdOrderByCreationDateDesc
                = repository.findByCountryIdOrderByCreationDateDesc(countryId);
        return groupMessagesByCountryIdOrderByCreationDateDesc.stream()
                .map(this::detachGroupMessageFields)
                .toList();
    }

    private GroupMessage detachGroupMessageFields(GroupMessage groupMessage) {
        return GroupMessage.builder()
                .id(groupMessage.getId())
                .content(groupMessage.getContent())
                .creationDate(groupMessage.getCreationDate())
                .build();
    }

    private GroupMessage buildGroupMessage(String content, User user, Country country) {
        return GroupMessage.builder()
                .content(content)
                .user(user)
                .country(country)
                .build();
    }
}
