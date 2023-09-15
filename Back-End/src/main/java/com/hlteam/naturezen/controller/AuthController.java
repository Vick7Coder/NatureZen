package com.hlteam.naturezen.controller;

import com.hlteam.naturezen.dto.request.LoginDto;
import com.hlteam.naturezen.dto.request.PasswordDto;
import com.hlteam.naturezen.dto.request.UserDto;
import com.hlteam.naturezen.dto.response.MessageResp;
import com.hlteam.naturezen.dto.response.UserI4Resp;
import com.hlteam.naturezen.entity.PasswordResetToken;
import com.hlteam.naturezen.entity.User;
import com.hlteam.naturezen.entity.VerificationToken;
import com.hlteam.naturezen.event.OnRegistrationCompleteEvent;
import com.hlteam.naturezen.repository.VerificationTokenRepository;
import com.hlteam.naturezen.security.jwt.JwtTokenService;
import com.hlteam.naturezen.security.service.UserDetailsImpl;
import com.hlteam.naturezen.service.EmailService;
import com.hlteam.naturezen.service.PasswordResetTokenService;
import com.hlteam.naturezen.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtTokenService.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(i ->i.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserI4Resp(userDetails.getUser()));
    }
    @PostMapping("/logout")
    @Operation(summary = "Đăng xuất")
    public ResponseEntity<?> logout(){
        ResponseCookie cookie = jwtTokenService.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResp("You've been logout!"));
    }

    @PostMapping("/register")
    @Operation(summary="Đăng ký")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto, final HttpServletRequest request){
        User user = userService.register(userDto);
        publisher.publishEvent(new OnRegistrationCompleteEvent(user, applicationUrl(request)));
        return ResponseEntity.ok(new MessageResp("User registered successfully! Please check your email to complete your registration."));
    }

    @GetMapping("/register/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token){
       // String url = applicationUrl(servletRequest)+"/register/resend-verification-token?token="+token;
        VerificationToken theToken = verificationTokenRepository.findByToken(token);
        if (theToken.getUser().isEnabled()){
            return ResponseEntity.badRequest().body(new MessageResp("This account has already been verified. Please login."));
        }

        String verificationResult = userService.validateToken(token);

        if (verificationResult.equalsIgnoreCase("valid")){
            return ResponseEntity.ok(new MessageResp("Email verified successfully. Now you can login to your account"));
        }
        return null;

      //  return ResponseEntity.badRequest().body(new MessageResp("Invalid verification link, <a href=\""+url+"\"> Get a new verification link. </a>"));
    }
    @GetMapping("/register/resend-verification-token")
    public ResponseEntity<?> resendVerificationToken(@RequestParam("token") String oldToken,
                                          final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User theUser = verificationToken.getUser();
        resendRegistrationVerificationTokenEmail(theUser, applicationUrl(request), verificationToken);
        return ResponseEntity.ok().body(new MessageResp("A new verification link has been sent to your email, please, check to activate your account"));
    }
    private void resendRegistrationVerificationTokenEmail(User theUser, String applicationUrl,
                                                          VerificationToken verificationToken) throws MessagingException, UnsupportedEncodingException {

        String url = applicationUrl+"/api/auth/register/verifyEmail?token="+verificationToken.getToken();
        emailService.sendVerificationEmail(theUser, url);
        log.info("Click the link to verify your registration :  {}", url);
    }
    @PostMapping("/password-reset-request")
    public ResponseEntity<?> resetPasswordRequest(@RequestBody PasswordDto passwordRequestUtil,
                                       final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {

        Optional<User> user = userService.findByEmail(passwordRequestUtil.getEmail());
        PasswordResetToken oldPass = passwordResetTokenService.IsHaving(passwordRequestUtil.getEmail());
        String passwordResetUrl = "";
        if(oldPass == null){

            if (user.isPresent()) {
                String passwordResetToken = UUID.randomUUID().toString();
                userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
                passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(servletRequest), passwordResetToken);
            }
            return ResponseEntity.ok().body(new MessageResp(passwordResetUrl));
        }
        else {
            PasswordResetToken newToken = passwordResetTokenService.generateNewResetPasswordToken(oldPass.getToken());
            User theUser = newToken.getUser();
            passwordResetUrl = passwordResetEmailLink(theUser, applicationUrl(servletRequest), newToken.getToken());
            return ResponseEntity.ok().body(new MessageResp(passwordResetUrl));
        }

    }
    private String passwordResetEmailLink(User user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/auth/reset-password?token="+passwordToken;
        emailService.sendPasswordResetVerificationEmail(user, url);
        log.info("Click the link to reset your password :  {}", url);
        return url;
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordDto passwordRequestUtil,
                                @RequestParam("token") String token){
        String tokenVerificationResult = userService.validatePasswordResetToken(token);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return ResponseEntity.badRequest().body(new MessageResp("Invalid token password reset token"));
        }
        User theUser = userService.findUserByPasswordToken(token);
        if (theUser!=null) {
            userService.changePassword(theUser, passwordRequestUtil.getNewPassword());
            return ResponseEntity.ok().body(new MessageResp("Password has been reset successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResp("Invalid password reset token"));
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDto requestUtil){
        Optional<User> user = userService.findByEmail(requestUtil.getEmail());
        if (!userService.oldPasswordIsValid(user.get(), requestUtil.getOldPassword())){
            return ResponseEntity.badRequest().body(new MessageResp("Incorrect old password"));
        }
        userService.changePassword(user.get(), requestUtil.getNewPassword());
        return ResponseEntity.ok().body(new MessageResp("Password changed successfully"));
    }



    public String applicationUrl (HttpServletRequest request){
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
