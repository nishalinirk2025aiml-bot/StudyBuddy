package com.example.studybuddy.controller;

import com.example.studybuddy.dto.StudentRequest;
import com.example.studybuddy.entity.Student;
import com.example.studybuddy.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    // ==========================================
    // REGISTER STUDENT
    // POST /api/students
    // ==========================================

    @PostMapping
    public ResponseEntity<Student> createStudent(
            @Valid @RequestBody StudentRequest request) {

        Student student =
                studentService.createStudent(request);

        return new ResponseEntity<>(
                student,
                HttpStatus.CREATED
        );
    }


    // ==========================================
    // GET ALL STUDENTS
    // GET /api/students
    // ==========================================

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {

        List<Student> students =
                studentService.getAllStudents();

        return ResponseEntity.ok(students);
    }


    // ==========================================
    // GET STUDENT BY ID
    // GET /api/students/{id}
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable Long id) {

        Student student =
                studentService.getStudentById(id);

        return ResponseEntity.ok(student);
    }


    // ==========================================
    // UPDATE STUDENT
    // PUT /api/students/{id}
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {

        Student student =
                studentService.updateStudent(id, request);

        return ResponseEntity.ok(student);
    }


    // ==========================================
    // DELETE STUDENT
    // DELETE /api/students/{id}
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.ok(
                "Student deleted successfully"
        );
    }
}