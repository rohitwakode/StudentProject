package com.Lucifer.StudentProject.model;

import com.Lucifer.StudentProject.enums.ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String password;

    private ROLE role=ROLE.USER;

    private Boolean isAccountVerified;

    private String VerifyOtp;
    private Long verifyOtpExpiredAt;

    private String resetOtp;
    private Long resetOtpExpiredAt;

    //token for reset password
    private String resetToken;
    private Long resetTokenExpiredAt;


}
