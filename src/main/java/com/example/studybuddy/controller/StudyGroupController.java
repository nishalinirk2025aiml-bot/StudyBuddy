package com.example.studybuddy.controller;

import com.example.studybuddy.dto.StudyGroupRequest;
import com.example.studybuddy.entity.StudyGroup;
import com.example.studybuddy.service.StudyGroupService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    public StudyGroupController(StudyGroupService studyGroupService) {
        this.studyGroupService = studyGroupService;
    }

    // ==========================================
    // CREATE STUDY GROUP
    // POST /api/groups
    // ==========================================

    @PostMapping
    public ResponseEntity<StudyGroup> createGroup(
            @Valid @RequestBody StudyGroupRequest request) {

        StudyGroup group = studyGroupService.createGroup(request);

        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }


    // ==========================================
    // GET ALL STUDY GROUPS
    // GET /api/groups
    // ==========================================

    @GetMapping
    public ResponseEntity<List<StudyGroup>> getAllGroups() {

        List<StudyGroup> groups =
                studyGroupService.getAllGroups();

        return ResponseEntity.ok(groups);
    }


    // ==========================================
    // GET GROUP BY ID
    // GET /api/groups/{id}
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<StudyGroup> getGroupById(
            @PathVariable Long id) {

        StudyGroup group =
                studyGroupService.getGroupById(id);

        return ResponseEntity.ok(group);
    }


    // ==========================================
    // GET GROUPS BY SUBJECT
    // GET /api/groups/subject/{subjectId}
    // ==========================================

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<StudyGroup>> getGroupsBySubject(
            @PathVariable Long subjectId) {

        List<StudyGroup> groups =
                studyGroupService.getGroupsBySubject(subjectId);

        return ResponseEntity.ok(groups);
    }


    // ==========================================
    // DELETE GROUP
    // DELETE /api/groups/{id}
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(
            @PathVariable Long id) {

        studyGroupService.deleteGroup(id);

        return ResponseEntity.ok(
                "Study group deleted successfully"
        );
    }
}