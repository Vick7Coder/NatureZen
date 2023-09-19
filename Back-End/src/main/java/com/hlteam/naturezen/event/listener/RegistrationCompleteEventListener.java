package com.hlteam.naturezen.event.listener;

import com.hlteam.naturezen.entity.User;
import com.hlteam.naturezen.event.OnRegistrationCompleteEvent;
import com.hlteam.naturezen.service.EmailService;
import com.hlteam.naturezen.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    private User user;
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        // 1. Get the newly registered user
        user = event.getUser();
        //2. create a verification token for new User
        String token = UUID.randomUUID().toString();
        //3. Save the verification token for the user
        userService.SavedVerificationToken(user, token);
        //4 Build the verification url to be sent to the user
        String url = event.getApplicationUrl()+"/api/auth/register/verifyEmail?token="+token;
        //5: Send the mail
        try {
            sendVerificationEmail(url);


        } catch (MessagingException | UnsupportedEncodingException exception){
            throw new RuntimeException(exception);
        }
        log.info("Click the link to verify your registration :  {}", url);
    }
    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        emailService.sendVerificationEmail(user, url);
    }
}
