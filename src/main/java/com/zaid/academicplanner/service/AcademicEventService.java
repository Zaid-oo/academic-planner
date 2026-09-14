package com.zaid.academicplanner.service;

import com.zaid.academicplanner.dto.EventAddRequest;
import com.zaid.academicplanner.dto.EventEditRequest;
import com.zaid.academicplanner.entity.AcademicEvent;
import com.zaid.academicplanner.entity.Course;
import com.zaid.academicplanner.exception.*;
import com.zaid.academicplanner.repository.AcademicEventRepository;
import org.springframework.stereotype.Service;
import com.zaid.academicplanner.entity.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AcademicEventService {
    private final AcademicEventRepository academicEventRepository;
    private final CourseService courseService;

    public AcademicEventService(AcademicEventRepository academicEventRepository, CourseService courseService) {
        this.academicEventRepository = academicEventRepository;
        this.courseService = courseService;
    }

    public AcademicEvent addEvent(EventAddRequest eventRequest){
        if(!eventRequest.getEndTime().isAfter(eventRequest.getStartTime()))
            throw new InvalidEventTimeException("Invalid event time");
        LocalDateTime dateTime = LocalDateTime.of(eventRequest.getDate(), eventRequest.getStartTime());
        if(!dateTime.isAfter(LocalDateTime.now()))
            throw new PastEventException("Invalid past event");
        Course course = null;
        if(eventRequest.getType() != EventType.VOLUNTARY_EVENT
                && eventRequest.getType() != EventType.OTHER) {
            if(eventRequest.getCourseCode() == null
                    || eventRequest.getCourseCode().isBlank())
                throw new MissingEventInputException("Missing event input");
            course = courseService.getCourseByCode(eventRequest.getCourseCode());
        }
        List<AcademicEvent> events = academicEventRepository.findAllByDateOrderByStartTimeAsc(eventRequest.getDate());
        for(AcademicEvent event : events){
            if(eventRequest.getStartTime().isBefore(event.getEndTime())
                    && eventRequest.getEndTime().isAfter(event.getStartTime()))
                throw new OverLappingEventsException("Event overlapping");
        }
        AcademicEvent academicEvent = new AcademicEvent(eventRequest.getName(),
                eventRequest.getType(), eventRequest.getStartTime(),eventRequest.getEndTime(),
                eventRequest.getDate(), eventRequest.getLocation(), eventRequest.isImportant(),
                eventRequest.getDescription(), course);
        return academicEventRepository.save(academicEvent);

    }

    public AcademicEvent getEventById(Integer eventId){
        Optional<AcademicEvent> event = academicEventRepository.findById(eventId);
        if(event.isEmpty())
            throw new EventNotFoundException("Event not found");
        return event.get();
    }

    public AcademicEvent removeEvent(Integer eventId){
        AcademicEvent event = getEventById(eventId);
        academicEventRepository.deleteById(eventId);
        return event;
    }

    public List<AcademicEvent> getAllEvents(){
        return academicEventRepository.findAll();
    }

    public AcademicEvent editEvent(Integer eventId, EventEditRequest eventEditRequest){
        AcademicEvent event = getEventById(eventId);
        if(!eventEditRequest.getEndTime().isAfter(eventEditRequest.getStartTime()))
            throw new InvalidEventTimeException("Invalid event time");
        LocalDateTime dateTime = LocalDateTime.of(eventEditRequest.getDate(), eventEditRequest.getStartTime());
        if(!dateTime.isAfter(LocalDateTime.now()))
            throw new PastEventException("Invalid past event");
        if(eventEditRequest.getType() != EventType.VOLUNTARY_EVENT
                && eventEditRequest.getType() != EventType.OTHER) {
            if(eventEditRequest.getCourseCode() == null
                    || eventEditRequest.getCourseCode().isBlank())
                throw new MissingEventInputException("Missing event input"); }
        List<AcademicEvent> events = academicEventRepository.findAllByDateOrderByStartTimeAsc(eventEditRequest.getDate());

        for(AcademicEvent searchedEvent : events){
            if(searchedEvent.getEventId().equals(event.getEventId()))
                continue;
            if(eventEditRequest.getStartTime().isBefore(searchedEvent.getEndTime())
                    && eventEditRequest.getEndTime().isAfter(searchedEvent.getStartTime()))
                throw new OverLappingEventsException("Event overlaps with an existing event"); }

        event.setName(eventEditRequest.getName());
        event.setType(eventEditRequest.getType());
        event.setStartTime(eventEditRequest.getStartTime());
        event.setEndTime(eventEditRequest.getEndTime());
        event.setDate(eventEditRequest.getDate());
        event.setLocation(eventEditRequest.getLocation());
        event.setImportant(eventEditRequest.isImportant());
        event.setDescription(eventEditRequest.getDescription());
        event.setRelatedCourse(null);
        if(eventEditRequest.getType() != EventType.VOLUNTARY_EVENT
                && eventEditRequest.getType() != EventType.OTHER){
            event.setRelatedCourse(courseService.getCourseByCode(eventEditRequest.getCourseCode())); }
        academicEventRepository.save(event);
        return event;

    }

    public AcademicEvent markEventImportant(Integer eventId){
        AcademicEvent event = getEventById(eventId);
        event.setImportant(true);
        academicEventRepository.save(event);
        return event;
    }

    public AcademicEvent unMarkEventImportant(Integer eventId){
        AcademicEvent event = getEventById(eventId);
        event.setImportant(false);
        academicEventRepository.save(event);
        return event;
    }

    public List<AcademicEvent> getEventsByDate(LocalDate date){
        return academicEventRepository.findAllByDateOrderByStartTimeAsc(date);
    }

    public List<AcademicEvent> getEventsByType(EventType type){
        return academicEventRepository.findAllByTypeOrderByDateAscStartTimeAsc(type);
    }

    public List<AcademicEvent> getEventsByCourse(String courseCode){
        courseService.getCourseByCode(courseCode);
        return academicEventRepository.findAllByRelatedCourseCourseCodeOrderByDateAscStartTimeAsc(courseCode);
    }

    public List<AcademicEvent> getImportantEvents(){
        return academicEventRepository.findAllByImportantOrderByDateAscStartTimeAsc(true);
    }

    public List<AcademicEvent> getUpcomingEvents(){
        return academicEventRepository.findAllByDateAfterOrDateEqualsAndStartTimeAfterOrderByDateAscStartTimeAsc
                (LocalDate.now(), LocalDate.now(), LocalTime.now());
    }

    public List<AcademicEvent> getPastEvents(){
        return academicEventRepository.findAllByDateBeforeOrDateEqualsAndEndTimeBeforeOrderByDateDescEndTimeDesc
                (LocalDate.now(), LocalDate.now(), LocalTime.now());
    }

}