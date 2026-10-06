package com.Lucifer.StudentProject.service;

import com.Lucifer.StudentProject.dto.UserReq;
import com.Lucifer.StudentProject.dto.UserRes;
import com.Lucifer.StudentProject.exception.EmaiException;
import com.Lucifer.StudentProject.exception.ResourceNotFound;
import com.Lucifer.StudentProject.model.User;
import com.Lucifer.StudentProject.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;


    public UserRes saved(UserReq user) {
        User user1 = userRepo.findByEmail(user.email());
        if (user1 != null) {
            throw new EmaiException("User with email " + user.email() + " already exists");
        }
        User user2 = new User();
        user2.setEmail(user.email());
        user2.setPassword(passwordEncoder.encode(user.password()));

        user2.setIsAccountVerified(false);

        user2.setResetOtpExpiredAt(0L);
        user2.setResetOtp(null);

        user2.setVerifyOtpExpiredAt(0L);
        user2.setVerifyOtp(null);


        User saved = userRepo.save(user2);
        return new UserRes(saved.getId(), saved.getEmail(),saved.getIsAccountVerified());
    }

    public void delete(Integer id) {
        User users= userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User  does not exist"));
        userRepo.delete(users);
    }
}
