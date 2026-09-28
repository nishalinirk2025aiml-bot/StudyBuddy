package com.example.studybuddy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "memberships",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "group_id",
                                "student_id"
                        }
                )
        }
)
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // =========================
    // STUDY GROUP
    // =========================

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "group_id",
            nullable = false
    )
    private StudyGroup studyGroup;


    // =========================
    // STUDENT
    // =========================

    @ManyToOne
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    private Student student;


    // =========================
    // JOIN DATE
    // =========================

    @Column(nullable = false)
    private LocalDateTime joinedAt;


    // =========================
    // CONSTRUCTORS
    // =========================

    public Membership() {
        this.joinedAt = LocalDateTime.now();
    }


    // =========================
    // GETTERS AND SETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}