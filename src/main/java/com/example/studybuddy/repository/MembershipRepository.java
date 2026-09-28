package com.example.studybuddy.repository;

import com.example.studybuddy.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository
        extends JpaRepository<Membership, Long> {

    // Find all members of a group
    List<Membership> findByStudyGroupId(Long groupId);

    // Find all groups joined by a student
    List<Membership> findByStudentId(Long studentId);

    // Check whether student already joined the group
    boolean existsByStudyGroupIdAndStudentId(
            Long groupId,
            Long studentId
    );

    // Find a specific membership
    Optional<Membership> findByStudyGroupIdAndStudentId(
            Long groupId,
            Long studentId
    );

    // Count current members in a group
    long countByStudyGroupId(Long groupId);

    // Delete a membership
    void deleteByStudyGroupIdAndStudentId(
            Long groupId,
            Long studentId
    );
}