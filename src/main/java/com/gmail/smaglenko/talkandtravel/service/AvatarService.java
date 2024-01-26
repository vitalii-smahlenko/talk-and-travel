package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.Avatar;
import com.gmail.smaglenko.talkandtravel.model.User;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {
    Avatar save(Avatar avatar);

    Avatar findByUserId(Long userId);

    Avatar createStandardAvatar(String username) throws IOException;
}
