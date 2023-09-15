package com.hlteam.naturezen.service.impl;

import com.hlteam.naturezen.dto.request.UserDto;
import com.hlteam.naturezen.entity.ERole;
import com.hlteam.naturezen.entity.Role;
import com.hlteam.naturezen.entity.User;
import com.hlteam.naturezen.entity.VerificationToken;
import com.hlteam.naturezen.exception.NotFoundException;
import com.hlteam.naturezen.exception.UserAlreadyExistsException;
import com.hlteam.naturezen.repository.RoleRepository;
import com.hlteam.naturezen.repository.UserRepository;
import com.hlteam.naturezen.repository.VerificationTokenRepository;
import com.hlteam.naturezen.service.PasswordResetTokenService;
import com.hlteam.naturezen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Override
    public User register(UserDto userDto) {
        Optional<User> userRequest = this.findByEmail(userDto.getEmail());
        if(userRequest.isPresent()){
            throw new UserAlreadyExistsException(
                    "User with email "+userDto.getEmail() + " already exists");
        }
        User nUser = new User();
        nUser.setUsername(userDto.getUsername());
        nUser.setFirstName(userDto.getFirstName());
        nUser.setLastName(userDto.getLastName());
        nUser.setEmail(userDto.getEmail());
        nUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        nUser.setGender(userDto.isGender());
        nUser.setBirthDay(userDto.getBirthDay());
        nUser.setPhoneNumber(userDto.getPhoneNumber());
        nUser.setAddress(userDto.getAddress());
        Set<String> strRole = userDto.getRole();
        Set<Role> roles = new HashSet<>();
        if(strRole==null){
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        }else {
            strRole.forEach(
                    role ->{
                        switch (role){
                            case "admin":
                                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                                roles.add(adminRole);
                                break;
                            case "mod":
                                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                                roles.add(modRole);
                                break;
                            default:
                                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                                roles.add(userRole);
                        }
                    }
            );
        }
        nUser.setRoles(roles);
        return userRepository.save(nUser);



    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Not Found User"));
    }

    @Override
    public User updateUser(UserDto userDto) {
        return null;
    }



    @Override
    public List<User> getUser() {
        return userRepository.findAll();
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
        VerificationToken token = tokenRepository.findByToken(tToken);
        if (token == null){
            return "Invalid verification token!";
        }
        User user = token.getUser();
        Calendar cal = Calendar.getInstance();
        if(token.getTokenExpirationTime().getTime() - cal.getTime().getTime() <=0){
            tokenRepository.delete(token);
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
        theUser.setPassword(passwordEncoder.encode(newPassword));
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
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
}
