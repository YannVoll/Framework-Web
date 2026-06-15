package com.example.courseplatform.dto;

import java.time.LocalDateTime;

public class EnrollmentResponse {
    private Long id;
    private Long userId;
    private String userName;
    private Long courseId;
    private String courseTitle;
    private LocalDateTime enrolledAt;
    private String status;

    public EnrollmentResponse(Long id, Long userId, String userName, Long courseId, String courseTitle, LocalDateTime enrolledAt, String status) {
        this.id = id; this.userId = userId; this.userName = userName;
        this.courseId = courseId; this.courseTitle = courseTitle;
        this.enrolledAt = enrolledAt; this.status = status;
    }
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public Long getCourseId() { return courseId; }
    public String getCourseTitle() { return courseTitle; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public String getStatus() { return status; }
}
