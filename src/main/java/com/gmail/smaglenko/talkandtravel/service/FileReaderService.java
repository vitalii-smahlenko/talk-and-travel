package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.Avatar;
import org.springframework.web.multipart.MultipartFile;

public interface FileReaderService {
    void readFile(MultipartFile file, Avatar avatar);
}
