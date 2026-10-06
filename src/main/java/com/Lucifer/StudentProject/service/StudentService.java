package com.Lucifer.StudentProject.service;

import com.Lucifer.StudentProject.dto.StudReq;
import com.Lucifer.StudentProject.dto.StudRes;
import com.Lucifer.StudentProject.exception.EmaiException;
import com.Lucifer.StudentProject.exception.ResourceNotFound;
import com.Lucifer.StudentProject.model.Student;
import com.Lucifer.StudentProject.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private StudentRepo studentRepo;

    public StudRes save(StudReq dto) {
        Student existingStudent = studentRepo.findByEmail(dto.email());
        if (existingStudent != null) {
            throw new EmaiException("student already exists");
        }
        Student student = new Student();
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setMobileNumber(dto.mobileNumber());
        student.setEmail(dto.email());
        student.setGender(dto.gender());
        student.setDateOfBirth(dto.dateOfBirth());
        student.setQualifications(dto.qualifications());
        student.setTechStack(dto.techStack());
        student.setDescription(dto.description());
        Student savedStudent = studentRepo.save(student);
        return mapToRes(savedStudent);
    }

    public Student getStudentById(Integer id) {
        return studentRepo.findById(id).orElseThrow(() -> new ResourceNotFound("student not found"));
    }
    public StudRes update(StudReq dto,Integer id) {
        Student existingStudent = getStudentById(id);
        if (dto.firstName() != null && !dto.firstName().trim().isEmpty()) {
            existingStudent.setFirstName(dto.firstName());
        }
        if (dto.lastName() != null && !dto.lastName().trim().isEmpty()) {
            existingStudent.setLastName(dto.lastName());
        }
        if (dto.mobileNumber() != null && !dto.mobileNumber().trim().isEmpty()) {
            existingStudent.setMobileNumber(dto.mobileNumber());
        }
        if (dto.email() != null && !dto.email().trim().isEmpty()) {
            existingStudent.setEmail(dto.email());
        }
        if (dto.dateOfBirth() != null){
            existingStudent.setDateOfBirth(dto.dateOfBirth());
        }
        if (dto.gender() != null) {
            existingStudent.setGender(dto.gender());
        }
        if (dto.qualifications() != null ) {
            existingStudent.setQualifications(dto.qualifications());
        }
        if (dto.techStack() != null ) {
            existingStudent.setTechStack(dto.techStack());
        }
        if (dto.description() != null && !dto.description().trim().isEmpty()) {
            existingStudent.setDescription(dto.description());
        }
        Student updatedStudent = studentRepo.save(existingStudent);
        return mapToRes(updatedStudent);
    }
    public void delete(Integer id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("student not found"));
        studentRepo.deleteById(id);
    }

    public StudRes findById(Integer id) {
        Student student =studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("student not found"));
        return mapToRes(student);
    }

    public List<StudRes> findAll() {
        return studentRepo.findAll()
                .stream()
                .map(this::mapToRes)
                .toList();
    }

    public List<StudRes>findAllByMobileNumber(String mobileNumber) {
        return studentRepo.findAllByMobileNumber(mobileNumber)
                .stream()
                .map(this::mapToRes)
                .toList();
    }

    public List<StudRes>findAllByFirstName(String firstName) {
        return studentRepo.findAllByFirstName(firstName)
                .stream()
                .map(this::mapToRes)
                .toList();
    }

    @Transactional
    public StudRes uploadFile(Integer id,MultipartFile file) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("student not found"));
        //validation

        if (file.isEmpty()) {
            throw new ResourceNotFound("file cannot be empty");
        }
        String contentType = file.getContentType();
        if (!"image/jpeg".equals(contentType)&& !"image/png".equals(contentType)) {
            throw new ResourceNotFound("only image/jpg are allowed");
        }
        if (file.getSize() >= 5*1024*1024) {
            throw new ResourceNotFound("file size is too large");
        }

        try {

            String fileName=fileStorageService.storeFile(file);
            student.setProfileImage(fileName);
            Student savedStudent = studentRepo.save(student);
            return mapToRes(savedStudent);

        }catch (IOException e){
            throw new ResourceNotFound("failed to store the image");
        }
    }

    StudRes mapToRes(Student student) {
        return new StudRes(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getMobileNumber(),
                student.getEmail(),
                student.getGender(),
                student.getDateOfBirth(),
                student.getQualifications(),
                student.getTechStack(),
                student.getDescription(),
                student.getProfileImage()
        );
    }
}
