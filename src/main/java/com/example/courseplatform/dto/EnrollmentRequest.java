package com.example.courseplatform.dto;

import jakarta.validation.constraints.NotNull;

public class EnrollmentRequest {
    @NotNull
    private Long courseId;

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
}
