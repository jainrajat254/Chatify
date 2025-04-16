package com.example.chatify.interceptor;

import com.example.chatify.user.Users;
import com.example.chatify.user.UserRepository;
import com.example.chatify.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing user with idp");
        getUserUsername(token).ifPresent(username -> {
            log.info("Synchronizing user having username {}", username);
            Optional<Users> optUser = userRepository.findByUsername(username);
            Users user = userMapper.fromTokenAttributes(token.getClaims());
            optUser.ifPresent(value -> user.setId(value.getId()));
            userRepository.save(user);

        });

    }

    private Optional<String> getUserUsername(Jwt token) {
        Map<String, Object> attributes = token.getClaims();
        if (attributes.containsKey("username")) {
            return Optional.of(attributes.get("username").toString());
        }
        return Optional.empty();

    }
}
