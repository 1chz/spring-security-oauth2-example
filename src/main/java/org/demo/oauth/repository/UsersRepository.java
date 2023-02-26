package org.demo.oauth.repository;

import org.demo.oauth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsBySocialId(String socialId);
}
