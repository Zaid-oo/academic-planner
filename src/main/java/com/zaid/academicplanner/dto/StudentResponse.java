package com.zaid.academicplanner.dto;

import com.zaid.academicplanner.entity.AcademicEvent;
import com.zaid.academicplanner.entity.Course;

import java.util.List;

public class StudentResponse {

    private Integer studentId;
    private String universityId;
    private String name;
    private List<Course> courses;
    private List<AcademicEvent> events;

    public StudentResponse() {
    }

    public StudentResponse(Integer studentId, String universityId, String name,
                           List<Course> courses, List<AcademicEvent> events) {
        this.studentId = studentId;
        this.universityId = universityId;
        this.name = name;
        this.courses = courses;
        this.events = events;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<AcademicEvent> getEvents() {
        return events;
    }

    public void setEvents(List<AcademicEvent> events) {
        this.events = events;
    }
}