package com.gmail.smaglenko.talkandtravel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    public ResponseEntity<String> sayWelcome(){
        return ResponseEntity.ok("Welcome to Talk&Travel chat!");
    }
}
