package com.zaid.academicplanner.service;

import com.zaid.academicplanner.dto.CourseCreateRequest;
import com.zaid.academicplanner.entity.Course;
import com.zaid.academicplanner.exception.CourseNotFoundException;
import com.zaid.academicplanner.exception.DuplicateCourseException;
import com.zaid.academicplanner.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(CourseCreateRequest courseRequest){
        if(courseRepository.existsByCourseCode(courseRequest.getCourseCode()))
            throw new DuplicateCourseException("Duplicate course");

        Course course = new Course(courseRequest.getCourseCode(), courseRequest.getCourseName());
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseByCode(String courseCode){
        Optional<Course> course = courseRepository.findByCourseCode(courseCode);
        if(course.isPresent())
            return course.get();
        else {
            throw new CourseNotFoundException("Course not found");
        }
    }

    public Course removeCourseByCode(String courseCode){
        Course course = getCourseByCode(courseCode);
        courseRepository.delete(course);
        return course;
    }

    public Course editCourseInfoByCode(String courseCode, String courseName){
        Course course = getCourseByCode(courseCode);
        course.setCourseName(courseName);
        return courseRepository.save(course);
    }
}