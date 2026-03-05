package com.kevinhe.budgeter.auth;


import com.kevinhe.budgeter.users.User;
import com.kevinhe.budgeter.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final AuthRepository authRepository;

    public AuthService(UserRepository userRepository, AuthRepository authRepository) {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    @Transactional
    public void ensureUserExists(String email, String displayName, String sub) {

        Optional<AuthIdentity> identity = authRepository.findByProviderAndSub("google", sub);

        if (identity.isEmpty()) {
            User newUser = userRepository.save(
                    new User(email, displayName)
            );

            authRepository.save(
                    new AuthIdentity(
                            newUser.getId(),
                            "google",
                            sub,
                            email
                    )
            );
        } else {
            AuthIdentity existing = identity.get();
            existing.setEmail(email);
            existing.setLastLoginAt(Instant.now());
            authRepository.save(existing);
        }
    }

}
