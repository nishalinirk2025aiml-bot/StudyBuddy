package com.example.studybuddy.service;

import com.example.studybuddy.dto.StudentRequest;
import com.example.studybuddy.entity.Student;
import com.example.studybuddy.exception.BusinessException;
import com.example.studybuddy.exception.ResourceNotFoundException;
import com.example.studybuddy.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // REGISTER STUDENT
    public Student createStudent(StudentRequest request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(
                    "Email already registered: " + request.getEmail());
        }

        Student student = new Student();

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());
        student.setDepartment(request.getDepartment());
        student.setYear(request.getYear());

        return studentRepository.save(student);
    }

    // GET ALL STUDENTS
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // GET STUDENT BY ID
    public Student getStudentById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with ID: " + id));
    }

    // UPDATE STUDENT
    public Student updateStudent(Long id, StudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with ID: " + id));

        studentRepository.findByEmail(request.getEmail())
                .ifPresent(existingStudent -> {

                    if (!existingStudent.getId().equals(id)) {
                        throw new BusinessException(
                                "Email already registered: "
                                        + request.getEmail());
                    }
                });

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());
        student.setDepartment(request.getDepartment());
        student.setYear(request.getYear());

        return studentRepository.save(student);
    }

    // DELETE STUDENT
    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Student not found with ID: " + id);
        }

        studentRepository.deleteById(id);
    }
}