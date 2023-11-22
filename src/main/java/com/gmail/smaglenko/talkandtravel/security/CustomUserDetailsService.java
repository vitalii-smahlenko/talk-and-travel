package com.gmail.smaglenko.talkandtravel.security;

import com.gmail.smaglenko.talkandtravel.model.User;
import com.gmail.smaglenko.talkandtravel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return new CustomUserDetails(user);
    }
}
