package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Participant;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.repository.ParticipantRepository;
import com.gmail.smaglenko.talkandtravel.service.ParticipantService;
import java.util.ArrayList;
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
    public Optional<Participant> findByUserIdAndCountryId(Long userId, Long countryId) {
        return repository.findByUserIdAndCountryId(userId, countryId);
    }

    @Override
    public Participant create(User user) {
        var participant = createNewParticipant(user);
        return save(participant);
    }

    private Participant createNewParticipant(User user) {
        return Participant.builder()
                .user(user)
                .countries(new ArrayList<>())
                .build();
    }
}
