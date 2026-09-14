package com.zaid.academicplanner.controller;

import com.zaid.academicplanner.dto.*;
import com.zaid.academicplanner.entity.AcademicEvent;
import com.zaid.academicplanner.entity.Course;
import com.zaid.academicplanner.entity.Lecture;
import com.zaid.academicplanner.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponse> addStudent(@RequestBody @Valid
                                                          StudentCreateRequest studentRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.addStudent(studentRequest));
    }

    @GetMapping("/{universityId}")
    public StudentResponse getStudentByUniversityId(@PathVariable String universityId,
                                                    Principal principal){

        checkStudentAccess(universityId, principal);

        return studentService.getStudentByUniversityId(universityId);
    }

    @DeleteMapping("/{universityId}")
    public StudentResponse removeStudent(@PathVariable String universityId, Principal principal){
        checkStudentAccess(universityId, principal);
        return studentService.removeStudent(universityId);
    }


    @PutMapping("/{universityId}")
    public StudentResponse editStudentInfo(@PathVariable String universityId,
                                           @RequestBody @Valid StudentEditRequest editRequest,
                                           Principal principal){
        checkStudentAccess(universityId, principal);
        return studentService.editStudentInfo(universityId, editRequest);
    }

    @PutMapping("/{universityId}/courses/{courseCode}")
    public List<Course> enrollStudentInCourse(@PathVariable String universityId,
                                              @PathVariable String courseCode,
                                              Principal principal){
        checkStudentAccess(universityId, principal);
        return studentService.enrollStudentInCourse(universityId, courseCode);
    }

    @PutMapping("/{universityId}/events/{eventId}")
    public List<AcademicEvent> enrollStudentInEvent(@PathVariable String universityId,
                                                    @PathVariable Integer eventId,
                                                    Principal principal){
        checkStudentAccess(universityId, principal);
        return studentService.enrollStudentInEvent(universityId, eventId);
    }

    @DeleteMapping("/{universityId}/courses/{courseCode}")
    public List<Course> removeStudentFromCourse(
            @PathVariable String universityId,
            @PathVariable String courseCode,
            Principal principal){
        checkStudentAccess(universityId, principal);
        return studentService.removeStudentFromCourse(universityId, courseCode);
    }

    @DeleteMapping("/{universityId}/events/{eventId}")
    public List<AcademicEvent> removeStudentFromEvent(
            @PathVariable String universityId,
            @PathVariable Integer eventId,
            Principal principal){
        checkStudentAccess(universityId, principal);
        return studentService.removeStudentFromEvent(universityId, eventId);
    }

    @GetMapping("/{universityId}/schedule")
    public List<Lecture> getLecturesSchedule(@PathVariable String universityId,
                                             Principal principal){
        checkStudentAccess(universityId, principal);
        return studentService.getLecturesSchedule(universityId);
    }

    @GetMapping("/{universityId}/courses")
    public List<Course> getStudentCourses(@PathVariable String universityId,
                                          Principal principal){
        checkStudentAccess(universityId, principal);
        return studentService.getStudentCourses(universityId);
    }

    @GetMapping("/{universityId}/events")
    public List<AcademicEvent> getStudentEvents(@PathVariable String universityId,
                                                Principal principal){
        checkStudentAccess(universityId, principal);
        return studentService.getStudentEvents(universityId);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest){
        return studentService.login(loginRequest);
    }

    private void checkStudentAccess(String universityId, Principal principal){
        if(!principal.getName().equals(universityId))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's data"
            );
    }


}