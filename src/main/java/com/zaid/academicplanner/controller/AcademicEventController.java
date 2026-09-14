package com.zaid.academicplanner.controller;

import com.zaid.academicplanner.dto.EventAddRequest;
import com.zaid.academicplanner.dto.EventEditRequest;
import com.zaid.academicplanner.entity.AcademicEvent;
import com.zaid.academicplanner.entity.EventType;
import com.zaid.academicplanner.service.AcademicEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/events")
public class AcademicEventController {

    private final AcademicEventService academicEventService;

    public AcademicEventController(AcademicEventService academicEventService) {
        this.academicEventService = academicEventService;
    }

    @PostMapping
    public ResponseEntity<AcademicEvent> addEvent(@RequestBody @Valid
                                                      EventAddRequest eventAddRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(academicEventService.addEvent(eventAddRequest));
    }

    @GetMapping("/{eventId}")
    public AcademicEvent getEventById(@PathVariable Integer eventId){
        return academicEventService.getEventById(eventId);
    }

    @DeleteMapping("/{eventId}")
    public AcademicEvent removeEvent(@PathVariable Integer eventId){
        return academicEventService.removeEvent(eventId);
    }

    @GetMapping
    public List<AcademicEvent> getAllEvents(){
        return academicEventService.getAllEvents();
    }

    @PutMapping("/{eventId}")
    public AcademicEvent editEvent(@PathVariable Integer eventId,
                                   @RequestBody @Valid EventEditRequest eventEditRequest){
        return academicEventService.editEvent(eventId, eventEditRequest);
    }

    @PutMapping("/{eventId}/important")
    public AcademicEvent markEventImportant(@PathVariable Integer eventId){
        return academicEventService.markEventImportant(eventId);
    }

    @PutMapping("/{eventId}/unimportant")
    public AcademicEvent unMarkEventImportant(@PathVariable Integer eventId){
        return academicEventService.unMarkEventImportant(eventId);
    }

    @GetMapping("/date/{date}")
    public List<AcademicEvent> getEventsByDate(@PathVariable LocalDate date){
        return academicEventService.getEventsByDate(date);
    }

    @GetMapping("/type/{type}")
    public List<AcademicEvent> getEventsByType(@PathVariable EventType type){
        return academicEventService.getEventsByType(type);
    }

    @GetMapping("/course/{courseCode}")
    public List<AcademicEvent> getEventsByCourse(@PathVariable String courseCode){
        return academicEventService.getEventsByCourse(courseCode);
    }

    @GetMapping("/important")
    public List<AcademicEvent> getImportantEvents(){
        return academicEventService.getImportantEvents();
    }

    @GetMapping("/upcoming")
    public List<AcademicEvent> getUpcomingEvents(){
        return academicEventService.getUpcomingEvents();
    }

    @GetMapping("/past")
    public List<AcademicEvent> getPastEvents(){
        return academicEventService.getPastEvents();
    }


}