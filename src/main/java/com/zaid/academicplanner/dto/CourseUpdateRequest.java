package com.zaid.academicplanner.dto;

import jakarta.validation.constraints.NotBlank;

public class CourseUpdateRequest {
    @NotBlank
    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
