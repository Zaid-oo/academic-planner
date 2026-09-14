package com.zaid.academicplanner.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class AcademicEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;
    @NotBlank
    private String name;
    @Enumerated(EnumType.STRING)
    @NotNull
    private EventType type;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @NotNull
    private LocalDate date;
    @NotBlank
    private String location;
    private boolean important;
    private String description;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course relatedCourse;

    public AcademicEvent() {
    }

    public AcademicEvent(String name, EventType type, LocalTime startTime, LocalTime endTime, LocalDate date,
                         String location, boolean important, String description, Course relatedCourse) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.location = location;
        this.important = important;
        this.description = description;
        this.relatedCourse = relatedCourse;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getRelatedCourse() {
        return relatedCourse;
    }

    public void setRelatedCourse(Course relatedCourse) {
        this.relatedCourse = relatedCourse;
    }
}