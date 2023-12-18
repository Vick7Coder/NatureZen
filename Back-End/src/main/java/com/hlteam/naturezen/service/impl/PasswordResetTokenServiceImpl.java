package com.hlteam.naturezen.service.impl;

import com.hlteam.naturezen.entity.PasswordResetToken;
import com.hlteam.naturezen.entity.User;
import com.hlteam.naturezen.repository.PasswordRequestTokenRepository;
import com.hlteam.naturezen.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    @Autowired
    private PasswordRequestTokenRepository passwordRequestTokenRepository;
    @Override
    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        PasswordResetToken passwordRestToken = new PasswordResetToken(passwordToken, user);
        passwordRequestTokenRepository.save(passwordRestToken);
    }
    @Override
    public String validatePasswordResetToken(String passwordResetToken) {
        PasswordResetToken passwordToken = passwordRequestTokenRepository.findByToken(passwordResetToken);
        if(passwordToken == null){
            return "Invalid Password Reset Token!";
        }
        User user = passwordToken.getUser();
        Calendar cal = Calendar.getInstance();
        if (passwordToken.getTokenExpirationTime().getTime() - cal.getTime().getTime() <= 0){
            return "Link already expired, resend link";
        }
        return "valid";
    }

    @Override
    public User findUserByPasswordToken(String passwordResetToken) {
        PasswordResetToken pToken = passwordRequestTokenRepository.findByToken(passwordResetToken);
        return pToken.getUser();
    }

    @Override
    public PasswordResetToken findPasswordResetToken(String token) {
        return passwordRequestTokenRepository.findByToken(token);
    }

    @Override
    public PasswordResetToken IsHaving(String email) {
        return passwordRequestTokenRepository.findByUserEmail(email);
    }

    @Override
    public PasswordResetToken generateNewResetPasswordToken(String oldToken) {
        PasswordResetToken passwordResetToken = passwordRequestTokenRepository.findByToken(oldToken);
        var passwordResetTokenTime = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setExpirationTime(passwordResetTokenTime.getTokenExpirationTime());
        return passwordRequestTokenRepository.save(passwordResetToken);
    }


}