package com.Lucifer.StudentProject.repo;

import com.Lucifer.StudentProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByEmail(String email);

}
