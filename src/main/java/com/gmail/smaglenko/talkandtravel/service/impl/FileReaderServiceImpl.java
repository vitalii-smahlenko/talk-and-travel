package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Avatar;
import com.gmail.smaglenko.talkandtravel.service.FileReaderService;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public void readFile(MultipartFile file, Avatar avatar) {
        try {
            avatar.setContent(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read avatar file with file name " + file.getName());
        }
    }
}
