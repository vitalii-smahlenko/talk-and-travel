package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Avatar;
import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.repository.AvatarRepository;
import com.gmail.smaglenko.talkandtravel.service.AvatarService;
import com.gmail.smaglenko.talkandtravel.service.FileReaderService;
import com.gmail.smaglenko.talkandtravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository repository;
    private final UserService userService;
    private final FileReaderService fileReaderService;

    @Override
    @Transactional
    public void uploadAvatar(User user, MultipartFile file) {
        if (file.getSize() > 1000000 || !file.getContentType().equals("image/jpeg")) {
            throw new IllegalArgumentException("Invalid avatar file. ");
        }
        Avatar avatar = new Avatar();
        avatar.setUser(user);
        fileReaderService.readFile(file, avatar);
        Avatar saved = repository.save(avatar);
        user.setAvatar(saved);
        userService.update(user);
    }
}
