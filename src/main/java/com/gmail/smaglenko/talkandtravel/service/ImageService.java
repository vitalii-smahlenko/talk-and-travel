package com.gmail.smaglenko.talkandtravel.service;

import java.io.IOException;

public interface ImageService {
    byte[] generateImage(String name) throws IOException;
}
