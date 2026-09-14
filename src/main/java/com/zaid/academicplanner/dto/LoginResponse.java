package com.zaid.academicplanner.dto;


public class LoginResponse {

    private String token;
    private StudentResponse response;

    public LoginResponse(String token, StudentResponse response) {
        this.token = token;
        this.response = response;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public StudentResponse getResponse() {
        return response;
    }

    public void setResponse(StudentResponse response) {
        this.response = response;
    }
}
