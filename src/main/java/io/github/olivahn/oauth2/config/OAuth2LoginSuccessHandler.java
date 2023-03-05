package io.github.olivahn.oauth2.config;

import static io.github.olivahn.oauth2.config.HttpCookieOAuth2AuthorizationRequest.REDIRECT_URI;
import io.github.olivahn.oauth2.model.OAuth2ProvidersUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

final class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private static final int ACCESS_TOKEN_EXPIRY_SECONDS = 1_800;

    private final JwtEncoder jwtEncoder;

    public OAuth2LoginSuccessHandler(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        response.setStatus(HttpStatus.FOUND.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.sendRedirect(targetUrl);
    }

    private String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String redirectUri = Arrays.stream(request.getCookies())
                .filter(cookie -> REDIRECT_URI.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("http://localhost:3000/tokens");

        Instant now = Instant.now();
        Jwt jwt = this.jwtEncoder.encode(JwtEncoderParameters.from(JwtClaimsSet.builder()
                .issuer("OAuth2 Client App")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ACCESS_TOKEN_EXPIRY_SECONDS))
                .subject(OAuth2ProvidersUser.from(authentication).id())
                .claim("scope", "ROLE_USER")
                .build()));

        return UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("access_token", jwt.getTokenValue())
                .build()
                .toUriString();
    }
}
