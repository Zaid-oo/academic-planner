package com.zaid.academicplanner.exception;

public class DuplicateLectureException extends RuntimeException{
    public DuplicateLectureException(String message) {
        super(message);
    }
}
