package com.hlteam.naturezen.dto.request;

import lombok.Data;

@Data
public class PasswordDto {
    private String email;
    private String oldPassword;
    private String newPassword;
}
