package com.hlteam.naturezen;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@SpringBootApplication
public class NatureZenApplication {

    public static void main(String[] args) {
        SpringApplication.run(NatureZenApplication.class, args);
    }

}
