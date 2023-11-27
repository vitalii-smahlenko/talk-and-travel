package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.Avatar;
import com.gmail.smaglenko.talkandtravel.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {
    void uploadAvatar(User user, MultipartFile file);

    Avatar save(Avatar avatar);
}
