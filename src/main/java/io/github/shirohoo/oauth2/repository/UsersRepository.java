package io.github.shirohoo.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsBySocialId(String socialId);
}
