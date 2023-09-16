package com.breiner.tesis.repository;

import com.breiner.tesis.entity.User;
import com.breiner.tesis.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    List<User> findAllByRole(Role role);
}
