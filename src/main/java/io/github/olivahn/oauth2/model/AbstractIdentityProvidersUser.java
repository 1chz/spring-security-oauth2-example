package io.github.olivahn.oauth2.model;

import io.github.olivahn.oauth2.repository.Authorities;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public abstract class AbstractIdentityProvidersUser implements IdentityProvidersUser {
    private final String clientRegistrationId;
    private final Map<String, Object> attributes;
    private final List<Authorities> authorities;

    public AbstractIdentityProvidersUser(OAuth2User oAuth2User, String clientRegistrationId, Map<String, Object> attributes) {
        this.clientRegistrationId = clientRegistrationId;
        this.attributes = attributes;
        this.authorities = oAuth2User.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(Authorities::new)
                .toList();
    }

    @Override
    public String getEmail() {
        return (String) getAttributes().get("email");
    }

    @Override
    public String getProvider() {
        return this.clientRegistrationId;
    }

    @Override
    public List<Authorities> getAuthorities() {
        return new ArrayList<>(authorities);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
