package com.example.studybuddy.controller;

import com.example.studybuddy.entity.Membership;
import com.example.studybuddy.service.MembershipService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(
            MembershipService membershipService) {

        this.membershipService = membershipService;
    }


    // ==========================================
    // JOIN GROUP
    //
    // POST /api/groups/{groupId}/join/{studentId}
    // ==========================================

    @PostMapping("/{groupId}/join/{studentId}")
    public ResponseEntity<Membership> joinGroup(
            @PathVariable Long groupId,
            @PathVariable Long studentId) {

        Membership membership =
                membershipService.joinGroup(
                        groupId,
                        studentId
                );

        return new ResponseEntity<>(
                membership,
                HttpStatus.CREATED
        );
    }


    // ==========================================
    // LEAVE GROUP
    //
    // DELETE /api/groups/{groupId}/leave/{studentId}
    // ==========================================

    @DeleteMapping("/{groupId}/leave/{studentId}")
    public ResponseEntity<String> leaveGroup(
            @PathVariable Long groupId,
            @PathVariable Long studentId) {

        membershipService.leaveGroup(
                groupId,
                studentId
        );

        return ResponseEntity.ok(
                "Student left the group successfully"
        );
    }


    // ==========================================
    // GET GROUP MEMBERS
    //
    // GET /api/groups/{groupId}/members
    // ==========================================

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<Membership>> getGroupMembers(
            @PathVariable Long groupId) {

        List<Membership> members =
                membershipService.getGroupMembers(groupId);

        return ResponseEntity.ok(members);
    }


    // ==========================================
    // REMOVE MEMBER
    //
    // DELETE
    // /api/groups/{groupId}/members/{studentId}
    //
    // creatorId identifies the group creator
    // ==========================================

    @DeleteMapping(
            "/{groupId}/members/{studentId}"
    )
    public ResponseEntity<String> removeMember(
            @PathVariable Long groupId,
            @PathVariable Long studentId,
            @RequestParam Long creatorId) {

        membershipService.removeMember(
                groupId,
                studentId,
                creatorId
        );

        return ResponseEntity.ok(
                "Member removed successfully"
        );
    }
}