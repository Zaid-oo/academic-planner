package com.zaid.academicplanner.controller;

import com.zaid.academicplanner.dto.CourseCreateRequest;
import com.zaid.academicplanner.dto.CourseUpdateRequest;
import com.zaid.academicplanner.entity.Course;
import com.zaid.academicplanner.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@Valid @RequestBody CourseCreateRequest courseRequest){
        Course course = courseService.addCourse(courseRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(course);
    }

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{courseCode}")
    public Course getCourseByCode(@PathVariable String courseCode){
        return courseService.getCourseByCode(courseCode);
    }

    @DeleteMapping("/{courseCode}")
    public Course removeCourseById(@PathVariable String courseCode){
        return courseService.removeCourseByCode(courseCode);
    }

    @PutMapping("/{courseCode}")
    public Course editCourseInfoByCode(@PathVariable String courseCode,
                                       @RequestBody @Valid CourseUpdateRequest request){
        return courseService.editCourseInfoByCode(courseCode, request.getCourseName());
    }
}