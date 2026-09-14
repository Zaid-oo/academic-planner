package com.zaid.academicplanner.repository;

import com.zaid.academicplanner.entity.AcademicEvent;
import com.zaid.academicplanner.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AcademicEventRepository extends JpaRepository<AcademicEvent, Integer> {
    List<AcademicEvent> findAllByDateOrderByStartTimeAsc(LocalDate date);
    List<AcademicEvent> findAllByTypeOrderByDateAscStartTimeAsc(EventType type);
    List<AcademicEvent> findAllByRelatedCourseCourseCodeOrderByDateAscStartTimeAsc(String courseCode);
    List<AcademicEvent> findAllByImportantOrderByDateAscStartTimeAsc(boolean important);
    List<AcademicEvent> findAllByDateAfterOrDateEqualsAndStartTimeAfterOrderByDateAscStartTimeAsc
            (LocalDate dateAfter, LocalDate dateEquals, LocalTime startTime);
    List<AcademicEvent> findAllByDateBeforeOrDateEqualsAndEndTimeBeforeOrderByDateDescEndTimeDesc
            (LocalDate dateBefore, LocalDate dateEquals, LocalTime time);

}