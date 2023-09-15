package com.hlteam.naturezen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Size(min = 2, max = 20, message = "Tên phải chứa từ 2 đến 20 ký tự!")
    private String firstName;
    @Size(min = 2, max = 20, message = "Họ phải chứa từ 2 đến 20 ký tự!")
    private String lastName;
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "Thiếu Username")
    @NotEmpty(message = "Thiếu Username")
    @Schema(description="Username",example="uteboiz",required=true)
    @Size(min = 5, max = 15, message = "Username chứa từ 5 đến 15 ký tự")
    private String username;
    @Size(min = 8, max = 20, message = "Password chứa từ 8 đến 20 ký tự")
    private String password;
    @Size(min = 10, max = 15, message = "Số điện thoại sai")
    private String phoneNumber;
    private boolean Gender;
    private LocalDateTime birthDay;
    private String address;
    private Set<String> role;
}
