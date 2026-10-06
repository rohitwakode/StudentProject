package com.Lucifer.StudentProject.repo;

import com.Lucifer.StudentProject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    Student findByEmail(String email);

    List<Student> findAllByFirstName(String firstName);


    List<Student> findAllByMobileNumber(String mobileNumber);

}