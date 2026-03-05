package com.kevinhe.budgeter.auth;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public record MeResponse(String email, String name, String sub) {}



    @GetMapping("/me")
    public MeResponse me(@AuthenticationPrincipal OidcUser user) {

        authService.ensureUserExists(user.getEmail(), user.getFullName(), user.getSubject());

        return new MeResponse(user.getEmail(), user.getFullName(), user.getSubject());
    }

}
