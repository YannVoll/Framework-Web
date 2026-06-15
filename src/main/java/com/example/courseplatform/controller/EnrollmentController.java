package com.example.courseplatform.controller;

import com.example.courseplatform.dto.EnrollmentResponse;
import com.example.courseplatform.entity.Enrollment;
import com.example.courseplatform.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<EnrollmentResponse> findAll() {
        return enrollmentService.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<EnrollmentResponse> findByUser(@PathVariable Long userId) {
        return enrollmentService.findByUserId(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        enrollmentService.cancel(id);
        return ResponseEntity.noContent().build();
    }

    private EnrollmentResponse toResponse(Enrollment e) {
        return new EnrollmentResponse(
            e.getId(),
            e.getUser().getId(), e.getUser().getName(),
            e.getCourse().getId(), e.getCourse().getTitle(),
            e.getEnrolledAt(), e.getStatus()
        );
    }
}
