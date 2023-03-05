package io.github.olivahn.oauth2.service;

import io.github.olivahn.oauth2.model.OAuth2ProvidersUser;
import io.github.olivahn.oauth2.repository.AuthoritiesRepository;
import io.github.olivahn.oauth2.repository.UsersRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends AbstractOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    public CustomOAuth2UserService(UsersRepository usersRepository, AuthoritiesRepository authoritiesRepository) {
        super(usersRepository, authoritiesRepository);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String registrationId = clientRegistration.getRegistrationId();

        OAuth2ProvidersUser oAuth2ProvidersUser = OAuth2ProvidersUser.of(registrationId, oAuth2User);
        super.registerAsMember(oAuth2ProvidersUser);

        return oAuth2User;
    }
}
