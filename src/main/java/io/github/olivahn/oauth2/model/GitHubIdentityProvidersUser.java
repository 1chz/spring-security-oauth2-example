package io.github.olivahn.oauth2.model;

import org.springframework.security.oauth2.core.user.OAuth2User;

public class GitHubIdentityProvidersUser extends AbstractIdentityProvidersUser {
    public GitHubIdentityProvidersUser(OAuth2User oAuth2User, String clientRegistrationId) {
        super(oAuth2User, clientRegistrationId, oAuth2User.getAttributes());
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
