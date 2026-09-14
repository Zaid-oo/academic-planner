package com.zaid.academicplanner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<String> handleCourseNotFoundException
            (CourseNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
    @ExceptionHandler(DuplicateCourseException.class)
    public ResponseEntity<String> handleDuplicateCourseException
            (DuplicateCourseException exception){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
    @ExceptionHandler(DuplicateLectureException.class)
    public ResponseEntity<String> handleDuplicateLectureException
            (DuplicateLectureException exception){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
    @ExceptionHandler(InvalidLectureTimeException.class)
    public ResponseEntity<String> InvalidLectureTimeException
            (InvalidLectureTimeException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
    @ExceptionHandler(LectureNotFoundException.class)
    public ResponseEntity<String> handleLectureNotFoundException
            (LectureNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateEventException.class)
    public ResponseEntity<String> handleDuplicateEventException
            (DuplicateEventException exception){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidEventTimeException.class)
    public ResponseEntity<String> handleInvalidEventTimeException
            (InvalidEventTimeException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(PastEventException.class)
    public ResponseEntity<String> handlePastEventException
            (PastEventException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
    @ExceptionHandler(MissingEventInputException.class)
    public ResponseEntity<String> handleMissingEventInputException
            (MissingEventInputException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
    @ExceptionHandler(OverLappingEventsException.class)
    public ResponseEntity<String> handleOverLappingEventsException
            (OverLappingEventsException exception){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<String> handleEventNotFoundException
            (EventNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException
            (StudentNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
    @ExceptionHandler(DuplicateStudentException.class)
    public ResponseEntity<String> DuplicateStudentException
            (DuplicateStudentException exception){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
    @ExceptionHandler(DuplicateEnrollmentException.class)
    public ResponseEntity<String> handleDuplicateEnrollmentException
            (DuplicateEnrollmentException exception){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(StudentNotEnrolledException.class)
    public ResponseEntity<String> handleStudentNotEnrolledException
            (StudentNotEnrolledException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(AuthenticationRelatedException.class)
    public ResponseEntity<String> handleAuthenticationRelatedException
            (AuthenticationRelatedException exception){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

}
