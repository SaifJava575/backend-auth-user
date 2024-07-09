package com.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.auth.entity.Users;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findByEmail(String email);
}
