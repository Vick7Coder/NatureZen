package com.hlteam.naturezen.repository;

import com.hlteam.naturezen.entity.PasswordResetToken;
import com.hlteam.naturezen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);



}
