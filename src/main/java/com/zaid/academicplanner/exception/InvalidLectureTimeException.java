package com.zaid.academicplanner.exception;

public class InvalidLectureTimeException extends RuntimeException{
    public InvalidLectureTimeException(String message) {
        super(message);
    }
}
