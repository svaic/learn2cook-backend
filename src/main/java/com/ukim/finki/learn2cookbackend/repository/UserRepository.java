package com.ukim.finki.learn2cookbackend.repository;

import com.ukim.finki.learn2cookbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
