package io.github.shirohoo.oauth2.service;

import io.github.shirohoo.oauth2.model.OAuth2ProvidersUser;
import io.github.shirohoo.oauth2.repository.AuthoritiesRepository;
import io.github.shirohoo.oauth2.repository.UsersRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends AbstractOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    public CustomOidcUserService(UsersRepository usersRepository, AuthoritiesRepository authoritiesRepository) {
        super(usersRepository, authoritiesRepository);
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUserService oidcUserService = new OidcUserService();

        OidcUser oidcUser = oidcUserService.loadUser(userRequest);
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String registrationId = clientRegistration.getRegistrationId();

        OAuth2ProvidersUser oAuth2ProvidersUser = OAuth2ProvidersUser.of(registrationId, oidcUser);
        super.registerAsMember(oAuth2ProvidersUser);

        return oidcUser;
    }
}
