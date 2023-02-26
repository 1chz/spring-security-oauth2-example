package org.demo.oauth.controller;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/users")
    public List<Object> user(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @AuthenticationPrincipal OidcUser oidcUser
    ) {
        if (oidcUser == null) {
            return List.of(authentication, oAuth2User);
        }
        return List.of(authentication, oAuth2User, oidcUser);
    }
}
