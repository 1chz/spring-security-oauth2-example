package io.github.olivahn.oauth2.model;

import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class NaverIdentityProvidersUser extends AbstractIdentityProvidersUser {
    @SuppressWarnings("unchecked")
    public NaverIdentityProvidersUser(OAuth2User oAuth2User, String clientRegistrationId) {
        super(oAuth2User, clientRegistrationId, (Map<String, Object>) oAuth2User.getAttributes().get("response"));
    }

    @Override
    public String getId() {
        return (String) getAttributes().get("id");
    }

    @Override
    public String getUsername() {
        return (String) getAttributes().get("name");
    }
}
