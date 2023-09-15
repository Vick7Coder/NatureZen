package com.hlteam.naturezen.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class InvalidVerificationTokenException extends RuntimeException{
    public InvalidVerificationTokenException(String message){
        super(message);
    }
}
