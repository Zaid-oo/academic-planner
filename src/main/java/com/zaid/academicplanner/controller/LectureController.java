package com.zaid.academicplanner.controller;


import com.zaid.academicplanner.dto.LectureAddRequest;
import com.zaid.academicplanner.dto.LectureEditRequest;
import com.zaid.academicplanner.entity.Lecture;
import com.zaid.academicplanner.service.LectureService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostMapping
    public ResponseEntity<Lecture> addLecture(@Valid @RequestBody LectureAddRequest lecture){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lectureService.addLecture(lecture));
    }

    @DeleteMapping("/{lectureId}")
    public Lecture removeLecture(@PathVariable Integer lectureId){
        return lectureService.removeLecture(lectureId);
    }

    @GetMapping
    public List<Lecture> getAllLectures(){
        return lectureService.getAllLectures();
    }

    @GetMapping("/{lectureId}")
    public Lecture getLectureById(@PathVariable Integer lectureId){
        return lectureService.getLectureById(lectureId);
    }

    @PutMapping("/{lectureId}")
    public Lecture editLectureInfo(@PathVariable Integer lectureId,
                                   @Valid @RequestBody LectureEditRequest lectureEditRequest){
        return lectureService.editLectureInfo(lectureId, lectureEditRequest);
    }

    @GetMapping("/course/{courseCode}")
    public List<Lecture> getLecturesByCourseCode(@PathVariable String courseCode){
        return lectureService.getLecturesByCourseCode(courseCode);
    }

}