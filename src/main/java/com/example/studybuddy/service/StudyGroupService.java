package com.example.studybuddy.service;

import com.example.studybuddy.dto.StudyGroupRequest;
import com.example.studybuddy.entity.Student;
import com.example.studybuddy.entity.StudyGroup;
import com.example.studybuddy.entity.Subject;
import com.example.studybuddy.exception.ResourceNotFoundException;
import com.example.studybuddy.repository.StudentRepository;
import com.example.studybuddy.repository.StudyGroupRepository;
import com.example.studybuddy.repository.SubjectRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    public StudyGroupService(
            StudyGroupRepository studyGroupRepository,
            SubjectRepository subjectRepository,
            StudentRepository studentRepository) {

        this.studyGroupRepository = studyGroupRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }

    // CREATE GROUP
    public StudyGroup createGroup(StudyGroupRequest request) {

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subject not found with ID: " + request.getSubjectId()));

        Student creator = studentRepository.findById(request.getCreatorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with ID: " + request.getCreatorId()));

        StudyGroup group = new StudyGroup();

        group.setGroupName(request.getGroupName());
        group.setMaxMembers(request.getMaxMembers());
        group.setDescription(request.getDescription());
        group.setSubject(subject);
        group.setCreator(creator);

        return studyGroupRepository.save(group);
    }

    // GET ALL GROUPS
    public List<StudyGroup> getAllGroups() {
        return studyGroupRepository.findAll();
    }

    // GET GROUP BY ID
    public StudyGroup getGroupById(Long id) {

        return studyGroupRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Study group not found with ID: " + id));
    }

    // GET GROUPS BY SUBJECT
    public List<StudyGroup> getGroupsBySubject(Long subjectId) {

        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException(
                    "Subject not found with ID: " + subjectId);
        }

        return studyGroupRepository.findBySubjectId(subjectId);
    }

    // DELETE GROUP
    public void deleteGroup(Long id) {

        if (!studyGroupRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Study group not found with ID: " + id);
        }

        studyGroupRepository.deleteById(id);
    }
}