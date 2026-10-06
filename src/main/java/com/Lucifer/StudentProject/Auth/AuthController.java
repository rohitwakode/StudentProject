package com.Lucifer.StudentProject.Auth;

import com.Lucifer.StudentProject.dto.UserReq;
import com.Lucifer.StudentProject.dto.UserRes;
import com.Lucifer.StudentProject.security.JwtService;
import com.Lucifer.StudentProject.security.LoginRes;
import com.Lucifer.StudentProject.service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    // Login
    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody Login login) {
        return ResponseEntity.ok(authService.login(login));
    }

    // Send OTP to email
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        return ResponseEntity.ok(authService.sendOtp(email));
    }

    // Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpReq otpReq) {
        String token = authService.verifyOtp(otpReq);
      return new ResponseEntity<>(token, HttpStatus.OK);
    }

    // Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody RestPassToken passToken) {
        return new ResponseEntity<>(authService.resetPassword(passToken), HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<UserRes>  register(@RequestBody UserReq userReq){
//        return new ResponseEntity<>(userService.saved(userReq), HttpStatus.OK);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<UserRes> delete(@PathVariable Integer id){
//        userService.delete(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
