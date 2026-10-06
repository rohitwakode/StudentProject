package com.Lucifer.StudentProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void endOtpEmail(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("From Student management System OTP verification");

        message.setText("Your OTP is : "+otp+"  \n\n This OTP is valid for 5 minutes.");

        mailSender.send(message);
    }

}
