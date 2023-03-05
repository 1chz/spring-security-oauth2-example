package io.github.olivahn.oauth2.service;

import io.github.olivahn.oauth2.model.OAuth2ProvidersUser;
import io.github.olivahn.oauth2.repository.Authorities;
import io.github.olivahn.oauth2.repository.AuthoritiesRepository;
import io.github.olivahn.oauth2.repository.Users;
import io.github.olivahn.oauth2.repository.UsersRepository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

abstract class AbstractOAuth2UserService {
    private final UsersRepository usersRepository;
    private final AuthoritiesRepository authoritiesRepository;

    public AbstractOAuth2UserService(UsersRepository usersRepository, AuthoritiesRepository authoritiesRepository) {
        this.usersRepository = usersRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    @Transactional
    protected void registerAsMember(OAuth2ProvidersUser OAuth2ProvidersUser) {
        if (!usersRepository.existsBySocialId(OAuth2ProvidersUser.id())) {
            Users users = Users.builder()
                    .socialId(OAuth2ProvidersUser.id())
                    .registrationId(OAuth2ProvidersUser.provider())
                    .username(OAuth2ProvidersUser.username())
                    .email(OAuth2ProvidersUser.email())
                    .build();

            List<Authorities> authorities = OAuth2ProvidersUser.authorities();
            authorities.forEach(it -> it.setUsers(users));
            authoritiesRepository.saveAll(authorities);
        }
    }
}
