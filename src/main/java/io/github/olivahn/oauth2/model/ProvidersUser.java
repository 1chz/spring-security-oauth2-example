package io.github.olivahn.oauth2.model;

import io.github.olivahn.oauth2.repository.Authorities;
import java.util.List;
import java.util.Map;

public interface ProvidersUser {
    String getProvider();

    String getId();

    String getUsername();

    String getPassword();

    String getEmail();

    List<Authorities> getAuthoritiesList();

    Map<String, Object> getAttributes();
}
