package com.zaid.academicplanner.dto;

import jakarta.validation.constraints.NotBlank;

public class StudentCreateRequest {

    @NotBlank
    private String universityId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}