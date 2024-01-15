package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.Message;

public interface MessageService {
    Message save(Message message);

    Message findById(Long id);
}
