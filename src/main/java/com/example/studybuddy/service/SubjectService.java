package com.example.studybuddy.service;

import com.example.studybuddy.dto.SubjectRequest;
import com.example.studybuddy.entity.Subject;
import com.example.studybuddy.exception.BusinessException;
import com.example.studybuddy.exception.ResourceNotFoundException;
import com.example.studybuddy.repository.SubjectRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    // CREATE SUBJECT
    public Subject createSubject(SubjectRequest request) {

        if (subjectRepository.existsByNameIgnoreCase(request.getName())) {
            throw new BusinessException(
                    "Subject already exists: " + request.getName());
        }

        Subject subject = new Subject();

        subject.setName(request.getName());
        subject.setDescription(request.getDescription());

        return subjectRepository.save(subject);
    }

    // GET ALL SUBJECTS
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // GET SUBJECT BY ID
    public Subject getSubjectById(Long id) {

        return subjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subject not found with ID: " + id));
    }

    // UPDATE SUBJECT
    public Subject updateSubject(Long id, SubjectRequest request) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subject not found with ID: " + id));

        if (subjectRepository.existsByNameIgnoreCase(request.getName())
                && !subject.getName().equalsIgnoreCase(request.getName())) {

            throw new BusinessException(
                    "Another subject already exists with name: "
                            + request.getName());
        }

        subject.setName(request.getName());
        subject.setDescription(request.getDescription());

        return subjectRepository.save(subject);
    }

    // DELETE SUBJECT
    public void deleteSubject(Long id) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subject not found with ID: " + id));

        if (subject.getStudyGroups() != null
                && !subject.getStudyGroups().isEmpty()) {

            throw new BusinessException(
                    "Cannot delete subject because study groups are using it.");
        }

        subjectRepository.delete(subject);
    }
}