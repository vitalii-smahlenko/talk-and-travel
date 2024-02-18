package com.gmail.smaglenko.talkandtravel.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({AuthenticationException.class, RegistrationException.class,
            NoSuchElementException.class, UsernameNotFoundException.class,
            UnsupportedFormatException.class, FileSizeExceededException.class,
            ImageWriteException.class, RuntimeException.class, ImageProcessingException.class})
    public ResponseEntity<Object> handleException(Exception e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            if (violation.getPropertyPath().toString().equals("userName")) {
                ApiException apiException = new ApiException(
                        violation.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        ZonedDateTime.now()
                );
                return ResponseEntity.badRequest().body(apiException);
            }
        }
        return ResponseEntity.badRequest().body(new ApiException("Validation failed",
                HttpStatus.BAD_REQUEST, ZonedDateTime.now()));
    }
}
