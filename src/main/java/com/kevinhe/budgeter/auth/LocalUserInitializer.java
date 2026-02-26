package com.kevinhe.budgeter.auth;

import com.kevinhe.budgeter.users.User;
import com.kevinhe.budgeter.users.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LocalUserInitializer {

    private static final UUID LOCAL_USER_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000001");

    private final UserRepository userRepository;

    public LocalUserInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        userRepository.findById(LOCAL_USER_ID)
            .orElseGet(() -> {
                User user = new User("local@local", "Local User");
                user.setId(LOCAL_USER_ID);
                return userRepository.save(user);
            });
    }
}