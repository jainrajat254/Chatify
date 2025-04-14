package com.example.chatify.service;

import com.example.chatify.dto.LoginRequest;
import com.example.chatify.dto.LoginResponse;
import com.example.chatify.dto.RegisterRequest;
import com.example.chatify.enums.Status;
import com.example.chatify.models.Users;
import com.example.chatify.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<LoginResponse> registerUser(RegisterRequest request) {
        Optional<Users> existingUser = authRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Users user = new Users();
        user.setFullName(request.getFullName());
        user.setBio(request.getBio());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(Status.ONLINE);

        Users savedUser = authRepository.save(user);

        LoginResponse response = new LoginResponse();
        response.setId(savedUser.getId()); // UUID or ObjectId
        response.setFullName(savedUser.getFullName());
        response.setUsername(savedUser.getUsername());
        response.setBio(savedUser.getBio());
        response.setStatus(Status.ONLINE);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<LoginResponse> loginUser(LoginRequest request) {
        Optional<Users> userOpt = authRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Users user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setUsername(user.getUsername());
        response.setBio(user.getBio());

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteUser(UUID id) {
        Optional<Users> userOpt = authRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        authRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}


