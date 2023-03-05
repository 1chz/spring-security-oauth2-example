package io.github.shirohoo.oauth2.controller;

import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {
    @GetMapping("/users")
    public Map<String, Object> user(Authentication authentication) {
        return ((Jwt) authentication.getPrincipal()).getClaims();
    }
}
