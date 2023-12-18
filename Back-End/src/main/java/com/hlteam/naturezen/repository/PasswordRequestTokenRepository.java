package com.hlteam.naturezen.repository;

import com.hlteam.naturezen.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRequestTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String passwordResetToken);
    PasswordResetToken findByUserEmail(String email);
}