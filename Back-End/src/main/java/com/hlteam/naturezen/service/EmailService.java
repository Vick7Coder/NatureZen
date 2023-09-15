package com.hlteam.naturezen.service;

import com.hlteam.naturezen.entity.User;

public interface EmailService {
    void sendVerificationEmail(User user, String url);
    void sendPasswordResetVerificationEmail(User user, String url);
}
