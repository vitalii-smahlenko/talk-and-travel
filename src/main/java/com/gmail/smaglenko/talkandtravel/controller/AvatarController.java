package com.gmail.smaglenko.talkandtravel.controller;

import com.gmail.smaglenko.talkandtravel.service.AvatarService;
import com.gmail.smaglenko.talkandtravel.util.constants.ApiPathConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;


@RestController
@RequestMapping(ApiPathConstants.API_BASE_PATH + "/avatars")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @GetMapping("/user/{userID}")
    private ResponseEntity<byte[]> getByUserId(@PathVariable Long userID) {
        var avatar = avatarService.findByUserId(userID);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(avatar.getContent());
    }
}
