package com.jsp.uber.services;

import com.jsp.uber.entites.User;
import com.jsp.uber.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserService implements UserDetailsService { // no other class can extend this class

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElse(null);
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
               .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + id));
    }
}
