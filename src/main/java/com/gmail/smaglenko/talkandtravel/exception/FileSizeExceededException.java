package com.gmail.smaglenko.talkandtravel.exception;

public class FileSizeExceededException extends RuntimeException{
    public FileSizeExceededException(String message) {
        super(message);
    }
}
