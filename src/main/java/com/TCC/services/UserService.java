package com.TCC.services;

import com.TCC.domain.user.User;
import com.TCC.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User already exists with this email.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());

        return userRepository.save(new User(user.getEmail(), encryptedPassword, user.getRole(), user.getHasGoogleAuth()));
    }
}