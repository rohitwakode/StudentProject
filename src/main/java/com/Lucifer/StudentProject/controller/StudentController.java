package com.Lucifer.StudentProject.controller;


import com.Lucifer.StudentProject.dto.StudReq;
import com.Lucifer.StudentProject.dto.StudRes;
import com.Lucifer.StudentProject.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Create Student
    @PostMapping
    public ResponseEntity<StudRes> save(
            @Valid @RequestBody StudReq dto) {
       return new ResponseEntity<>(studentService.save(dto), HttpStatus.OK);
    }

    // Get Student By ID
    @GetMapping("/{id}")
    public ResponseEntity<StudRes> findById(
            @PathVariable Integer id) {
        return new ResponseEntity<>(studentService.findById(id), HttpStatus.OK);
    }

    // Get All Students
    @GetMapping
    public ResponseEntity<List<StudRes>> findAll() {
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }

    // Update Student
    @PutMapping("/{id}")
    public ResponseEntity<StudRes> update(
            @PathVariable Integer id,
            @RequestBody StudReq dto) {
        StudRes response = studentService.update(dto, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) {
        studentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/mobile/{mobileNumber}")
    public ResponseEntity<List<StudRes>> findAllByMobileNumber(
            @PathVariable String mobileNumber) {

        return ResponseEntity.ok(
                studentService.findAllByMobileNumber(mobileNumber)
        );
    }

    @GetMapping("/firstname/{firstName}")
    public ResponseEntity<List<StudRes>> findAllByFirstName(
            @PathVariable String firstName) {

        return ResponseEntity.ok(
                studentService.findAllByFirstName(firstName)
        );
    }



}
