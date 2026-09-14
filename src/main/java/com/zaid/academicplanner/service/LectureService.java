package com.zaid.academicplanner.service;

import com.zaid.academicplanner.dto.LectureAddRequest;
import com.zaid.academicplanner.dto.LectureEditRequest;
import com.zaid.academicplanner.entity.Course;
import com.zaid.academicplanner.entity.Lecture;
import com.zaid.academicplanner.exception.LectureNotFoundException;
import com.zaid.academicplanner.exception.InvalidLectureTimeException;
import com.zaid.academicplanner.repository.LectureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;
    private final CourseService courseService;

    public LectureService(LectureRepository lectureRepository, CourseService courseService) {
        this.lectureRepository = lectureRepository;
        this.courseService = courseService;
    }

    public Lecture addLecture(LectureAddRequest lectureRequest){
        Course course = courseService.getCourseByCode(lectureRequest.getCourseCode());
        if(!lectureRequest.getEndTime().isAfter(lectureRequest.getStartTime()))
            throw new InvalidLectureTimeException("Invalid lecture time");
        Lecture lecture = new Lecture(course,
                lectureRequest.getHall(), lectureRequest.getDayOfWeek(),
                lectureRequest.getStartTime(), lectureRequest.getEndTime());
        return lectureRepository.save(lecture);
    }

    public Lecture removeLecture(Integer lectureId){
        Lecture lecture = getLectureById(lectureId);
        lectureRepository.deleteById(lectureId);
        return lecture;
    }

    public List<Lecture> getAllLectures(){
        return lectureRepository.findAll();
    }

    public Lecture getLectureById(Integer lectureId){
        Optional<Lecture> lecture = lectureRepository.findById(lectureId);
        if(lecture.isEmpty())
            throw new LectureNotFoundException("Lecture not found");
        return lecture.get();
    }

    public Lecture editLectureInfo(Integer lectureId, LectureEditRequest lectureRequest){
        Lecture lecture = getLectureById(lectureId);
        Course course = courseService.getCourseByCode(lectureRequest.getCourseCode());
        lecture.setRelatedCourse(course);
        if(!lectureRequest.getEndTime().isAfter(lectureRequest.getStartTime()))
            throw new InvalidLectureTimeException("Invalid lecture time");
        lecture.setStartTime(lectureRequest.getStartTime());
        lecture.setEndTime(lectureRequest.getEndTime());
        lecture.setDayOfWeek(lectureRequest.getDayOfWeek());
        lecture.setHall(lectureRequest.getHall());
        return lectureRepository.save(lecture);
    }

    public List<Lecture> getLecturesByCourseCode(String courseCode){
        courseService.getCourseByCode(courseCode);
        return lectureRepository.findAllByRelatedCourseCourseCode(courseCode);
    }

}