package com.example.studybuddy.repository;

import com.example.studybuddy.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository
        extends JpaRepository<Subject, Long> {

    // Find subject by name
    Optional<Subject> findByNameIgnoreCase(String name);

    // Check whether subject already exists
    boolean existsByNameIgnoreCase(String name);
}