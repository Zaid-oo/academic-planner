package com.zaid.academicplanner.dto;

import jakarta.validation.constraints.NotBlank;

public class CourseCreateRequest {

    @NotBlank
    private String courseCode;

    @NotBlank
    private String courseName;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}