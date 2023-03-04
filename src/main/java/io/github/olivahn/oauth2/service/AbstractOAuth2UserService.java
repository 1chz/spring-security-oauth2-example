package io.github.olivahn.oauth2.service;

import io.github.olivahn.oauth2.model.IdentityProvidersUser;
import io.github.olivahn.oauth2.repository.Authorities;
import io.github.olivahn.oauth2.repository.AuthoritiesRepository;
import io.github.olivahn.oauth2.repository.Users;
import io.github.olivahn.oauth2.repository.UsersRepository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractOAuth2UserService {
    private final UsersRepository usersRepository;
    private final AuthoritiesRepository authoritiesRepository;

    public AbstractOAuth2UserService(UsersRepository usersRepository, AuthoritiesRepository authoritiesRepository) {
        this.usersRepository = usersRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    @Transactional
    protected void registerAsMember(IdentityProvidersUser identityProvidersUser) {
        if (!usersRepository.existsBySocialId(identityProvidersUser.getId())) {
            Users users = Users.builder()
                    .socialId(identityProvidersUser.getId())
                    .registrationId(identityProvidersUser.getProvider())
                    .username(identityProvidersUser.getUsername())
                    .email(identityProvidersUser.getEmail())
                    .build();

            List<Authorities> authorities = identityProvidersUser.getAuthorities();
            authorities.forEach(it -> it.setUsers(users));
            authoritiesRepository.saveAll(authorities);
        }
    }
}
