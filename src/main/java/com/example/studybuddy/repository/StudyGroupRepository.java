package com.example.studybuddy.repository;

import com.example.studybuddy.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroupRepository
        extends JpaRepository<StudyGroup, Long> {

    // Find all groups belonging to a subject
    List<StudyGroup> findBySubjectId(Long subjectId);

    // Find groups created by a particular student
    List<StudyGroup> findByCreatorId(Long creatorId);

    // Search groups by name
    List<StudyGroup> findByGroupNameContainingIgnoreCase(
            String groupName
    );

    // Search groups by subject and group name
    List<StudyGroup> findBySubjectIdAndGroupNameContainingIgnoreCase(
            Long subjectId,
            String groupName
    );
}