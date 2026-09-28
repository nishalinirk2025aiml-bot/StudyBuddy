package com.example.studybuddy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "study_groups")
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // =========================
    // GROUP INFORMATION
    // =========================

    @Column(nullable = false)
    private String groupName;


    @Column(nullable = false)
    private Integer maxMembers;


    @Column(length = 1000)
    private String description;


    // =========================
    // CREATOR
    // =========================

    @ManyToOne
    @JoinColumn(
            name = "creator_id",
            nullable = false
    )
    private Student creator;


    // =========================
    // SUBJECT
    // =========================

    @ManyToOne
    @JoinColumn(
            name = "subject_id",
            nullable = false
    )
    private Subject subject;


    // =========================
    // MEMBERSHIPS
    // =========================

    @JsonIgnore
    @OneToMany(
            mappedBy = "studyGroup",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Membership> memberships = new ArrayList<>();


    // =========================
    // CONSTRUCTORS
    // =========================

    public StudyGroup() {
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


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public Integer getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Student getCreator() {
        return creator;
    }

    public void setCreator(Student creator) {
        this.creator = creator;
    }


    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }


    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }
}