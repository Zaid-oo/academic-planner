package com.zaid.academicplanner.exception;

public class MissingEventInputException extends RuntimeException{
    public MissingEventInputException(String message) {
        super(message);
    }
}
