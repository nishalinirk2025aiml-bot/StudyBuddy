package com.example.studybuddy.controller;

import com.example.studybuddy.dto.SubjectRequest;
import com.example.studybuddy.entity.Subject;
import com.example.studybuddy.service.SubjectService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @PostMapping
    public ResponseEntity<Subject> createSubject(
            @Valid @RequestBody SubjectRequest request) {

        Subject subject =
                subjectService.createSubject(request);

        return new ResponseEntity<>(
                subject,
                HttpStatus.CREATED
        );
    }


    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {

        return ResponseEntity.ok(
                subjectService.getAllSubjects()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                subjectService.getSubjectById(id)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(
            @PathVariable Long id,
            @Valid @RequestBody SubjectRequest request) {

        return ResponseEntity.ok(
                subjectService.updateSubject(id, request)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(
            @PathVariable Long id) {

        subjectService.deleteSubject(id);

        return ResponseEntity.ok(
                "Subject deleted successfully"
        );
    }
}