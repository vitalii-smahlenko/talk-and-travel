package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.Participant;
import com.gmail.smaglenko.talkandtravel.model.User;
import java.util.Optional;

public interface ParticipantService {
    Participant save(Participant participant);

    Participant findById(Long id);

    Optional<Participant> findByUser(User user);

    Participant create(User user);
}
