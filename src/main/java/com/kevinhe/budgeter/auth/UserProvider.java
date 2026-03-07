package com.kevinhe.budgeter.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
@Profile("!local")
public class UserProvider implements CurrentUserProvider {

    private final AuthRepository authRepository;

    public UserProvider(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UUID currentUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        if (oidcUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "oidcUser doesn't exist");
        }

        AuthIdentity authIdentity = authRepository
                .findByProviderAndSub("google", oidcUser.getSubject())
                .orElseThrow(() -> new RuntimeException("Auth identity not found"));

        return authIdentity.getUserId();
    }

}
