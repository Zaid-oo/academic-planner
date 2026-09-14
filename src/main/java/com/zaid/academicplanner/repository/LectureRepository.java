package com.zaid.academicplanner.repository;

import com.zaid.academicplanner.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {

    List<Lecture> findAllByRelatedCourseCourseCode(String courseCode);

}