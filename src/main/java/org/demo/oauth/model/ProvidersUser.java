package org.demo.oauth.model;

import java.util.List;
import java.util.Map;
import org.demo.oauth.repository.Authorities;

public interface ProvidersUser {
    String getProvider();

    String getId();

    String getUsername();

    String getPassword();

    String getEmail();

    List<Authorities> getAuthoritiesList();

    Map<String, Object> getAttributes();
}
