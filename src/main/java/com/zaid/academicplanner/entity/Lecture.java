package com.zaid.academicplanner.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
public class Lecture {

    public Lecture(Course relatedCourse, String hall,
                   DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.relatedCourse = relatedCourse;
        this.hall = hall;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Lecture() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lectureId;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    @NotNull
    private DayOfWeek dayOfWeek;
    @NotBlank
    private String hall;
    @JoinColumn(name = "course_id")
    @ManyToOne
    @NotNull
    private Course relatedCourse;

    public Integer getLectureId() {
        return lectureId;
    }

    public void setLectureId(Integer lectureId) {
        this.lectureId = lectureId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public Course getRelatedCourse() {
        return relatedCourse;
    }

    public void setRelatedCourse(Course relatedCourse) {
        this.relatedCourse = relatedCourse;
    }
}