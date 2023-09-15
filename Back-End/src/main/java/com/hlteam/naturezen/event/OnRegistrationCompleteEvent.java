package com.hlteam.naturezen.event;

import com.hlteam.naturezen.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    public OnRegistrationCompleteEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }

}
