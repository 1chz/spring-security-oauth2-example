package io.github.olivahn.oauth2.model;

import org.springframework.security.oauth2.core.user.OAuth2User;

public class KakaoIdentityProvidersUser extends AbstractIdentityProvidersUser {
    public KakaoIdentityProvidersUser(OAuth2User oAuth2User, String clientRegistrationId) {
        super(oAuth2User, clientRegistrationId, oAuth2User.getAttributes());
    }

    @Override
    public String getId() {
        return (String) getAttributes().get("sub");
    }

    @Override
    public String getUsername() {
        return (String) getAttributes().get("nickname");
    }
}
