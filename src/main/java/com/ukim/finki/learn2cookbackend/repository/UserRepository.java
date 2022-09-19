package com.ukim.finki.learn2cookbackend.repository;

import com.ukim.finki.learn2cookbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<UserDetails> findByUsername(String id);
}
