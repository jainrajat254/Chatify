package com.example.chatify.repository;

import com.example.chatify.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByUsername(String username);
}
