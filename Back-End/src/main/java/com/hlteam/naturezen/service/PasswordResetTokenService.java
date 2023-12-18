package com.hlteam.naturezen.service;

import com.hlteam.naturezen.entity.PasswordResetToken;
import com.hlteam.naturezen.entity.User;

public interface PasswordResetTokenService {
    void createPasswordResetTokenForUser(User user, String passwordToken);
    String validatePasswordResetToken(String passwordResetToken);
    User findUserByPasswordToken(String passwordResetToken);
    PasswordResetToken findPasswordResetToken(String token);
    PasswordResetToken IsHaving(String email);

    PasswordResetToken generateNewResetPasswordToken(String oldToken);

}