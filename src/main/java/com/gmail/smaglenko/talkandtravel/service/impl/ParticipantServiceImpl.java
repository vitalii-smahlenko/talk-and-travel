package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Participant;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.repository.ParticipantRepository;
import com.gmail.smaglenko.talkandtravel.service.ParticipantService;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository repository;

    @Override
    public Participant save(Participant participant) {
        return repository.save(participant);
    }

    @Override
    public Participant findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Can not find participant by ID: " + id)
        );
    }

    @Override
    public Optional<Participant> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public Participant create(User user) {
        Participant participant = Participant.builder()
                .user(user)
                .countries(new ArrayList<>())
                .build();
        return save(participant);
    }
}
