package com.example.lucas.controllers;

import com.example.lucas.dto.*;
import com.example.lucas.entities.*;
import com.example.lucas.repositories.*;
import com.example.lucas.services.*;
import com.example.lucas.utils.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authManager, UserService userService,
                          JwtUtils jwtUtils, UserRepository userRepository) {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        boolean isAdmin = "ADMIN".equalsIgnoreCase(req.getRole());
        User u = userService.register(req.getEmail(), req.getPassword(), req.getName(), isAdmin);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow();
        var roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        String token = jwtUtils.generateToken(user.getEmail(), roles);
        return ResponseEntity.ok(new AuthResponse(token, user.getEmail()));
    }
}
