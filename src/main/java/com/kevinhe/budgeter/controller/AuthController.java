package com.kevinhe.budgeter.controller;


import com.kevinhe.budgeter.service.AuthService;
import com.kevinhe.budgeter.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public record MeResponse(UUID id, String email, String name) {}



    @GetMapping("/me")
    public MeResponse me(@AuthenticationPrincipal OAuth2User oAuth2User) {

        if (oAuth2User == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }
        System.out.println(oAuth2User.getAttributes());

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String sub = oAuth2User.getAttribute("sub");

        if (email == null || name == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing OAuth user attributes");
        }

        User user = authService.ensureUserExists(email, name, sub);

        return new MeResponse(user.getId(), user.getEmail(), user.getDisplayName());
    }

}
