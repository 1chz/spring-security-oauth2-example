package org.demo.oauth.model;

import java.util.Map;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class NaverProvidersUser extends AbstractProvidersUser {
    @SuppressWarnings("unchecked")
    public NaverProvidersUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User, clientRegistration, (Map<String, Object>) oAuth2User.getAttributes().get("response"));
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
