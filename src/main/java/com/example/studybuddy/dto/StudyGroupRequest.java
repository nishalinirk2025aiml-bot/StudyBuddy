package com.example.studybuddy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class StudyGroupRequest {

    @NotBlank(message = "Group name is required")
    private String groupName;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Creator ID is required")
    private Long creatorId;

    @NotNull(message = "Maximum members is required")
    @Positive(message = "Maximum members must be greater than 0")
    private Integer maxMembers;

    private String description;


    // =========================
    // CONSTRUCTORS
    // =========================

    public StudyGroupRequest() {
    }


    // =========================
    // GETTERS AND SETTERS
    // =========================

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }


    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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
}