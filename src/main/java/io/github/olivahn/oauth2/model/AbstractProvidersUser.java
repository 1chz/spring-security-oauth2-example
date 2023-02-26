package io.github.olivahn.oauth2.model;

import io.github.olivahn.oauth2.repository.Authorities;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public abstract class AbstractProvidersUser implements ProvidersUser {
    private final OAuth2User oAuth2User;
    private final ClientRegistration clientRegistration;
    private final Map<String, Object> attributes;

    public AbstractProvidersUser(OAuth2User oAuth2User, ClientRegistration clientRegistration, Map<String, Object> attributes) {
        this.oAuth2User = oAuth2User;
        this.clientRegistration = clientRegistration;
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getEmail() {
        return (String) getAttributes().get("email");
    }

    @Override
    public String getProvider() {
        return clientRegistration.getRegistrationId();
    }

    @Override
    public List<Authorities> getAuthoritiesList() {
        return oAuth2User.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(Authorities::new)
                .toList();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
