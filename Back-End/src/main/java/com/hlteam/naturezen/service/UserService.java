package com.hlteam.naturezen.service;

import com.hlteam.naturezen.dto.request.UserDto;
import com.hlteam.naturezen.entity.User;
import com.hlteam.naturezen.entity.VerificationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(UserDto userDto);
    User getUserByUsername(String username);
    User updateUser(UserDto userDto);

    List<User> getUser();
    Optional<User> findByEmail(String email);
    void SavedVerificationToken(User user, String verificationToken);
    String validateToken(String tToken);

    VerificationToken generateNewVerificationToken(String oldToken);
    void changePassword(User theUser, String newPassword);

    String validatePasswordResetToken(String token);

    User findUserByPasswordToken(String token);

    void createPasswordResetTokenForUser(User user, String passwordResetToken);

    boolean oldPasswordIsValid(User user, String oldPassword);


}
