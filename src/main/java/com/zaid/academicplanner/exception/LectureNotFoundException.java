package com.zaid.academicplanner.exception;

public class LectureNotFoundException extends RuntimeException{
    public LectureNotFoundException(String message) {
        super(message);
    }
}
