package io.github.olivahn.oauth2.model;

import java.util.Arrays;

enum OAuth2Providers {
    GOOGLE,
    GITHUB,
    NAVER,
    KAKAO,
    ;

    public static OAuth2Providers from(String providersName) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(providersName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("'%s' is invalid OAuth2 Provider".formatted(providersName)));
    }
}
