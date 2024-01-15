package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Message;
import com.gmail.smaglenko.talkandtravel.repository.MessageRepository;
import com.gmail.smaglenko.talkandtravel.service.MessageService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;

    @Override
    public Message save(Message message) {
        return repository.save(message);
    }

    @Override
    public Message findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Can not find message by ID: " + id)
        );
    }
}
