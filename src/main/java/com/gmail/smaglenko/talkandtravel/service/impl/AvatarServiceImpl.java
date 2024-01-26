package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Avatar;
import com.gmail.smaglenko.talkandtravel.repository.AvatarRepository;
import com.gmail.smaglenko.talkandtravel.service.AvatarService;
import com.gmail.smaglenko.talkandtravel.service.ImageService;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import java.io.IOException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository repository;
    private final ImageService imageService;
    private final UserService userService;

    @Override
    public Avatar save(Avatar avatar) {
        return repository.save(avatar);
    }

    @Override
    @Transactional
    public Avatar findByUserId(Long userId) {
        return repository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("Can not find avatar by user ID: " + userId)
        );
    }

    @Override
    public Avatar createStandardAvatar(String username) throws IOException {
        var standardAvatar = imageService.generateImage(username);
        return buildAvatar(standardAvatar);
    }

    private Avatar buildAvatar(byte[] standardAvatar) {
        return Avatar.builder()
                .content(standardAvatar)
                .build();
    }
}
