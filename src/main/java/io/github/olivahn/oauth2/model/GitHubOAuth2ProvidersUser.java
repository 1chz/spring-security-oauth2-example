package io.github.olivahn.oauth2.model;

import org.springframework.security.oauth2.core.user.OAuth2User;

final class GitHubOAuth2ProvidersUser extends AbstractOAuth2ProvidersUser {
    public GitHubOAuth2ProvidersUser(OAuth2User oAuth2User, String clientRegistrationId) {
        super(oAuth2User, clientRegistrationId, oAuth2User.getAttributes());
    }

    @Override
    public String id() {
        return String.valueOf(attributes().get("id"));
    }

    @Override
    public String username() {
        return (String) attributes().get("name");
    }
}
