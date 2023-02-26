package org.demo.oauth.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GitHubProvidersUser extends AbstractProvidersUser {
    public GitHubProvidersUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User, clientRegistration, oAuth2User.getAttributes());
    }

    @Override
    public String getId() {
        return String.valueOf(getAttributes().get("id"));
    }

    @Override
    public String getUsername() {
        return (String) getAttributes().get("name");
    }
}
