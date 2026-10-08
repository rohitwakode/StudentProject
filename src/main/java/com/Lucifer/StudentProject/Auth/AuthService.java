package com.Lucifer.StudentProject.Auth;

import com.Lucifer.StudentProject.exception.InvalidCredentials;
import com.Lucifer.StudentProject.model.User;
import com.Lucifer.StudentProject.repo.UserRepo;
import com.Lucifer.StudentProject.security.JwtService;
import com.Lucifer.StudentProject.security.LoginRes;
import com.Lucifer.StudentProject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager auth;

    //login
    public LoginRes login(Login login) {
        Authentication authentication=auth
                .authenticate(new UsernamePasswordAuthenticationToken(login.email(), login.password()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername());
        return new LoginRes(token);
    }

    //sending otp
    public String sendOtp(String email){
        User user = userRepo.findByEmail(email);
        if(user==null){
            return "If the email is registered, an OTP has been sent";
        }
        String otp= String.format("%06d",new SecureRandom().nextInt(1_000_000));

        user.setResetOtp(passwordEncoder.encode(otp));
        user.setResetOtpExpiredAt(System.currentTimeMillis()+(1000*60*5));

        userRepo.save(user);
        emailService.endOtpEmail(user.getEmail(), otp);
        return "If the email is registered, an OTP has been sent";
    }

    //generate Token
    private String generateToken(){

        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(tokenBytes);
    }

    //verifyOtp
    public String verifyOtp(OtpReq otpReq){
        User user = userRepo.findByEmail(otpReq.email());
        if(user==null){
            throw new InvalidCredentials("invalid otp");
        }
        if(user.getResetOtp()==null){
            throw  new InvalidCredentials("Otp not Genrated");
        }

        if(user.getResetOtpExpiredAt()==null || user.getResetOtpExpiredAt()<System.currentTimeMillis()){
            throw  new InvalidCredentials("Otp expired");
        }
        if(!passwordEncoder.matches(otpReq.otp(), user.getResetOtp())){
            throw  new InvalidCredentials("invalid otp");
        }

        //if otp is correct then genrate reset password token
        String token = generateToken();

        user.setResetToken(passwordEncoder.encode(token));
        user.setResetTokenExpiredAt(System.currentTimeMillis()+(1000*60*6));

        //after generating token the value of reset otp and its expiration will convert into null
        user.setResetOtp(null);
        user.setResetOtpExpiredAt(null);
        userRepo.save(user);
        return token;
    }

    //reset the password
    public String resetPassword(RestPassToken request){
        User user=userRepo.findByEmail(request.email());
        if(user==null){
            throw  new InvalidCredentials("Invalid request");
        }
        if (user.getResetToken() == null) {
            throw new InvalidCredentials("Invalid reset token");
        }
        if(user.getResetTokenExpiredAt()==null || user.getResetTokenExpiredAt()<System.currentTimeMillis()){
            throw  new InvalidCredentials("Reset token expired");
        }
        if (!passwordEncoder.matches(request.resetToken(), user.getResetToken())) {
            throw new InvalidCredentials("Invalid reset token");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiredAt(null);
        userRepo.save(user);
        return "password reset successfully";
    }

}
