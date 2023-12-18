package com.hlteam.naturezen.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import com.hlteam.naturezen.dto.request.ChangePasswordRequest;
import com.hlteam.naturezen.entity.User;
import com.hlteam.naturezen.entity.VerificationToken;
import com.hlteam.naturezen.event.OnRegistrationCompleteEvent;
import com.hlteam.naturezen.repository.VerificationTokenRepository;
import com.hlteam.naturezen.security.jwt.JwtTokenService;
import com.hlteam.naturezen.security.service.UserDetailsImpl;
import com.hlteam.naturezen.service.EmailService;
import com.hlteam.naturezen.service.PasswordResetTokenService;
import com.hlteam.naturezen.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.hlteam.naturezen.dto.request.CreateUserRequest;
import com.hlteam.naturezen.dto.request.LoginRequest;
import com.hlteam.naturezen.dto.response.MessageResponse;
import com.hlteam.naturezen.dto.response.UserInfoResponse;


import io.swagger.v3.oas.annotations.Operation;
@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtUtils;

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @PostMapping("/login")
    @Operation(summary="Đăng nhập")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        User us = userService.getUserByUsername(loginRequest.getUsername());
        if(us == null || us.isEnabled() == false){
            return ResponseEntity.badRequest().body(new MessageResponse("User does not exist or is not enabled!"));
        }
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new UserInfoResponse(userDetails.getUser().getId(), userDetails.getUsername(), userDetails.getUser().getEmail(), roles));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid username or password!"));

        }
    }

    @PostMapping("/register")
    @Operation(summary="Đăng ký")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserRequest cRequest, final HttpServletRequest request){
        User user = userService.register(cRequest);
        publisher.publishEvent(new OnRegistrationCompleteEvent(user, applicationUrl(request)));
        return ResponseEntity.ok(new MessageResponse("User registered successfully! Please check your email to complete your registration."));
    }
    @GetMapping("/register/verifyEmail")
    @Operation(summary = "Xác thực email")
    public String verifyEmail(@RequestParam("token") String token, HttpServletRequest servletRequest){
        String url = applicationUrl(servletRequest)+"/api/auth/register/resend-verification-token?token="+token;
        VerificationToken theToken = verificationTokenRepository.findByToken(token);
        if (theToken.getUser().isEnabled()){
            return "This account has already been verified. Please login.";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login to your account";
        }
        return emailService.ResendVerificationTokenForm(url);
    }
    @GetMapping("/register/resend-verification-token")
    @Operation(summary = "Gửi lại token xác thực đến mail")
    public ResponseEntity<?> resendVerificationToken(@RequestParam("token") String oldToken,
                                                     final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User theUser = verificationToken.getUser();
        resendRegistrationVerificationTokenEmail(theUser, applicationUrl(request), verificationToken);
        return ResponseEntity.ok().body(new MessageResponse("A new verification link has been sent to your email, please, check to activate your account"));
    }
    private void resendRegistrationVerificationTokenEmail(User theUser, String applicationUrl,
                                                          VerificationToken verificationToken) throws MessagingException, UnsupportedEncodingException {

        String url = applicationUrl+"/api/auth/register/verifyEmail?token="+verificationToken.getToken();
        emailService.sendVerificationEmail(theUser, url);
        log.info("Click the link to verify your registration :  {}", url);
    }

    @PostMapping("/logout")
    @Operation(summary="Đăng xuất")
    public ResponseEntity<?> logoutUser() {
      ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
      return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
          .body(new MessageResponse("You've been logout!"));
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest requestUtil){
        Optional<User> user = userService.findByEmail(requestUtil.getEmail());
        if (!userService.oldPasswordIsValid(user.get(), requestUtil.getOldPassword())){
            return ResponseEntity.badRequest().body(new MessageResponse("Incorrect old password"));
        }
        userService.changePassword(user.get(), requestUtil.getNewPassword());
        return ResponseEntity.ok().body(new MessageResponse("Password changed successfully"));
    }

    public String applicationUrl (HttpServletRequest request){
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
