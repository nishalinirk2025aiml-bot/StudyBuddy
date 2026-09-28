package com.example.studybuddy.service;

import com.example.studybuddy.entity.Membership;
import com.example.studybuddy.entity.Student;
import com.example.studybuddy.entity.StudyGroup;
import com.example.studybuddy.exception.BusinessException;
import com.example.studybuddy.exception.ResourceNotFoundException;
import com.example.studybuddy.repository.MembershipRepository;
import com.example.studybuddy.repository.StudentRepository;
import com.example.studybuddy.repository.StudyGroupRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final StudentRepository studentRepository;

    public MembershipService(
            MembershipRepository membershipRepository,
            StudyGroupRepository studyGroupRepository,
            StudentRepository studentRepository) {

        this.membershipRepository = membershipRepository;
        this.studyGroupRepository = studyGroupRepository;
        this.studentRepository = studentRepository;
    }

    // JOIN GROUP
    public Membership joinGroup(Long groupId, Long studentId) {

        StudyGroup group = studyGroupRepository.findById(groupId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Study group not found with ID: " + groupId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with ID: " + studentId));

        // Check duplicate membership
        if (membershipRepository
                .existsByStudyGroupIdAndStudentId(groupId, studentId)) {

            throw new BusinessException(
                    "Student is already a member of this group.");
        }

        // Check group capacity
        long currentMembers =
                membershipRepository.countByStudyGroupId(groupId);

        if (currentMembers >= group.getMaxMembers()) {

            throw new BusinessException(
                    "Study group is full. Maximum members: "
                            + group.getMaxMembers());
        }

        Membership membership = new Membership();

        membership.setStudyGroup(group);
        membership.setStudent(student);

        return membershipRepository.save(membership);
    }

    // LEAVE GROUP
    public void leaveGroup(Long groupId, Long studentId) {

        if (!studyGroupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException(
                    "Study group not found with ID: " + groupId);
        }

        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException(
                    "Student not found with ID: " + studentId);
        }

        if (!membershipRepository
                .existsByStudyGroupIdAndStudentId(groupId, studentId)) {

            throw new BusinessException(
                    "Student is not a member of this group.");
        }

        membershipRepository
                .deleteByStudyGroupIdAndStudentId(groupId, studentId);
    }

    // GET GROUP MEMBERS
    public List<Membership> getGroupMembers(Long groupId) {

        if (!studyGroupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException(
                    "Study group not found with ID: " + groupId);
        }

        return membershipRepository.findByStudyGroupId(groupId);
    }

    // REMOVE MEMBER
    public void removeMember(
            Long groupId,
            Long studentId,
            Long creatorId) {

        StudyGroup group = studyGroupRepository.findById(groupId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Study group not found with ID: " + groupId));

        // Check creator
        if (!group.getCreator().getId().equals(creatorId)) {

            throw new BusinessException(
                    "Only the group creator can remove members.");
        }

        // Cannot remove creator
        if (group.getCreator().getId().equals(studentId)) {

            throw new BusinessException(
                    "The group creator cannot be removed.");
        }

        // Check membership
        if (!membershipRepository
                .existsByStudyGroupIdAndStudentId(groupId, studentId)) {

            throw new ResourceNotFoundException(
                    "Student is not a member of this group.");
        }

        membershipRepository
                .deleteByStudyGroupIdAndStudentId(groupId, studentId);
    }
}