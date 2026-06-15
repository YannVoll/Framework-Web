package com.example.courseplatform.dto;

import java.time.LocalDateTime;

public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private String instructor;
    private Double price;
    private boolean active;
    private LocalDateTime createdAt;

    public CourseResponse(Long id, String title, String description, String instructor, Double price, boolean active, LocalDateTime createdAt) {
        this.id = id; this.title = title; this.description = description;
        this.instructor = instructor; this.price = price; this.active = active; this.createdAt = createdAt;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getInstructor() { return instructor; }
    public Double getPrice() { return price; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
