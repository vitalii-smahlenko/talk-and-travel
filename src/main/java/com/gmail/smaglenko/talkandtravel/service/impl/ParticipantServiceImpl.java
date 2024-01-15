package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Country;
import com.gmail.smaglenko.talkandtravel.model.Participant;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.repository.ParticipantRepository;
import com.gmail.smaglenko.talkandtravel.service.ParticipantService;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<Country> findCountriesByUser(Long userId) {
        return repository.findCountriesByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("The User is not a participant of any Country")
        );
    }
}
