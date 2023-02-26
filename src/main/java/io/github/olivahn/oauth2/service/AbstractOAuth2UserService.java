package io.github.olivahn.oauth2.service;

import io.github.olivahn.oauth2.model.GitHubProvidersUser;
import io.github.olivahn.oauth2.model.GoogleProvidersUser;
import io.github.olivahn.oauth2.model.KakaoProvidersUser;
import io.github.olivahn.oauth2.model.NaverProvidersUser;
import io.github.olivahn.oauth2.model.ProvidersUser;
import io.github.olivahn.oauth2.repository.Authorities;
import io.github.olivahn.oauth2.repository.AuthoritiesRepository;
import io.github.olivahn.oauth2.repository.Users;
import io.github.olivahn.oauth2.repository.UsersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class AbstractOAuth2UserService {
    private final UsersRepository usersRepository;
    private final AuthoritiesRepository authoritiesRepository;

    protected ProvidersUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        return switch (clientRegistration.getRegistrationId()) {
            case "google", "GOOGLE" -> new GoogleProvidersUser(oAuth2User, clientRegistration);
            case "github", "GITHUB" -> new GitHubProvidersUser(oAuth2User, clientRegistration);
            case "naver", "NAVER" -> new NaverProvidersUser(oAuth2User, clientRegistration);
            case "kakao", "KAKAO" -> new KakaoProvidersUser(oAuth2User, clientRegistration);
            default -> throw new BadCredentialsException("Invalid OAuth2 Provider");
        };
    }

    @Transactional
    protected void register(ProvidersUser providersUser) {
        if (!usersRepository.existsBySocialId(providersUser.getId())) {
            Users users = Users.builder()
                    .socialId(providersUser.getId())
                    .registrationId(providersUser.getProvider())
                    .username(providersUser.getUsername())
                    .password(providersUser.getPassword())
                    .email(providersUser.getEmail())
                    .build();

            List<Authorities> authorities = providersUser.getAuthoritiesList();
            authorities.forEach(it -> it.setUsers(users));
            authoritiesRepository.saveAll(authorities);
        }
    }
}
