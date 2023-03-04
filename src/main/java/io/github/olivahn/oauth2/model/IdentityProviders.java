package io.github.olivahn.oauth2.model;

import java.util.Arrays;

public enum IdentityProviders {
    GOOGLE,
    GITHUB,
    NAVER,
    KAKAO,
    ;

    public static IdentityProviders from(String idp) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(idp))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid OAuth2 Provider"));
    }
}
