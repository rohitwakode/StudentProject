package com.Lucifer.StudentProject.security;

import com.Lucifer.StudentProject.model.User;
import com.Lucifer.StudentProject.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(username);
        if(user==null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrinciple(user);
    }
}
