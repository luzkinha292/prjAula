package com.example.lucas.services;

import com.example.lucas.entities.Role;
import com.example.lucas.entities.User;
import com.example.lucas.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository ur, PasswordEncoder encoder) {
        this.userRepository = ur; this.encoder = encoder;
    }

    public User register(String email, String password, String name, boolean isAdmin) {
        if (userRepository.existsByEmail(email)) throw new RuntimeException("Email já cadastrado");
        User u = new User(email, encoder.encode(password), name,
                Set.of(isAdmin ? Role.ROLE_ADMIN : Role.ROLE_USER));
        return userRepository.save(u);
    }
}
