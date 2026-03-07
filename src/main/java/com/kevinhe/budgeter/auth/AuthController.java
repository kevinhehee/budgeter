package com.kevinhe.budgeter.auth;


import com.kevinhe.budgeter.users.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public record MeResponse(UUID id, String email, String name) {}



    @GetMapping("/me")
    public MeResponse me(@AuthenticationPrincipal OidcUser oidcUser) {

        User user = authService.ensureUserExists(oidcUser.getEmail(), oidcUser.getFullName(), oidcUser.getSubject());

        return new MeResponse(user.getId(), user.getEmail(), user.getDisplayName());
    }

}
