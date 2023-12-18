package com.hlteam.naturezen.service.impl;

import java.util.*;

import com.hlteam.naturezen.dto.request.ChangePasswordRequest;
import com.hlteam.naturezen.dto.request.CreateUserRequest;
import com.hlteam.naturezen.dto.request.UpdateProfileRequest;
import com.hlteam.naturezen.entity.ERole;
import com.hlteam.naturezen.entity.Role;
import com.hlteam.naturezen.entity.User;
import com.hlteam.naturezen.entity.VerificationToken;
import com.hlteam.naturezen.exception.NotFoundException;
import com.hlteam.naturezen.repository.RoleRepository;
import com.hlteam.naturezen.repository.UserRepository;
import com.hlteam.naturezen.repository.VerificationTokenRepository;
import com.hlteam.naturezen.service.PasswordResetTokenService;
import com.hlteam.naturezen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Override
    public User register(CreateUserRequest request) {
        // TODO Auto-generated method stub
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        Set<String> strRoles = request.getRole();
          Set<Role> roles = new HashSet<>();
      
          if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
          } else {
            strRoles.forEach(role -> {
              switch (role) {
              case "admin":
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
      
                break;
              case "mod":
                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);
      
                break;
              default:
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
              }
            });
          }
          user.setRoles(roles);
          return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
      // TODO Auto-generated method stub
      User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User"));
      return user;
    }

    @Override
    public User updateUser(UpdateProfileRequest request) {
      // TODO Auto-generated method stub
      User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("Not Found User"));
      user.setFirstname(request.getFirstname());
      user.setLastname(request.getLastname());
      user.setEmail(request.getEmail());
      user.setCountry(request.getCountry());
      user.setState(request.getState());
      user.setAddress(request.getAddress());
      user.setPhone(request.getPhone());
      userRepository.save(user);
      return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void SavedVerificationToken(User user, String verToken) {
        var verificationToken = new VerificationToken(verToken, user);
        tokenRepository.save(verificationToken);

    }

    @Override
    public String validateToken(String tToken) {
        VerificationToken tk = tokenRepository.findByToken(tToken);
        if (tk == null){
            return "Invalid verification token!";
        }
        User user = tk.getUser();
        Calendar cal = Calendar.getInstance();
        if(tk.getExpirationTime().getTime() - cal.getTime().getTime() <= 0){
            //tokenRepository.delete(tk);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        var verificationTokenTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(verificationTokenTime.getTokenExpirationTime());
        return tokenRepository.save(verificationToken);
    }

    @Override
    public void changePassword(User theUser, String newPassword) {
        theUser.setPassword(encoder.encode(newPassword));
        userRepository.save(theUser);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetToken(token);
    }

    @Override
    public User findUserByPasswordToken(String token) {
        return passwordResetTokenService.findUserByPasswordToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordResetToken);
    }

    @Override
    public boolean oldPasswordIsValid(User user, String oldPassword) {
        return encoder.matches(oldPassword, user.getPassword());
    }




}
