package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.UserObject;
import ru.otus.spring.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LibraryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name)
                .map(this::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by name: " + name));
    }

    private UserDetails toUserDetails(UserObject userObject) {
        return User.withUsername(userObject.getName())
                .password(userObject.getPassword())
                .roles(userObject.getRole())
                .build();
    }
}
