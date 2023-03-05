package io.github.shirohoo.oauth2.model;

import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

final class NaverOAuth2ProvidersUser extends AbstractOAuth2ProvidersUser {
    @SuppressWarnings("unchecked")
    public NaverOAuth2ProvidersUser(OAuth2User oAuth2User, String clientRegistrationId) {
        super(oAuth2User, clientRegistrationId, (Map<String, Object>) oAuth2User.getAttributes().get("response"));
    }

    @Override
    public String id() {
        return (String) attributes().get("id");
    }

    @Override
    public String username() {
        return (String) attributes().get("name");
    }
}
