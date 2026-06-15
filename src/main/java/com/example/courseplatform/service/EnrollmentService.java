package com.example.courseplatform.service;

import com.example.courseplatform.entity.Course;
import com.example.courseplatform.entity.Enrollment;
import com.example.courseplatform.entity.User;
import com.example.courseplatform.exception.ResourceNotFoundException;
import com.example.courseplatform.repository.EnrollmentRepository;
import com.example.courseplatform.repository.CourseRepository;
import com.example.courseplatform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             UserRepository userRepository,
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public Enrollment create(String email, Long courseId, String paymentMethod) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", 0L));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", courseId));
        if (enrollmentRepository.existsByUserAndCourse(user, course)) {
            throw new IllegalArgumentException("Você já está matriculado neste curso.");
        }
        String method = (paymentMethod == null || paymentMethod.isBlank()) ? "PIX" : paymentMethod;
        return enrollmentRepository.save(new Enrollment(user, course, method));
    }

    public boolean isEnrolled(String email, Long courseId) {
        return getEnrollment(email, courseId) != null;
    }

    public Enrollment getEnrollment(String email, Long courseId) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return null;
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return null;
        return enrollmentRepository.findByUserAndCourse(user, course).orElse(null);
    }

    public List<Enrollment> findByUserId(Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }

    public List<Enrollment> findActiveByUserId(Long userId) {
        return enrollmentRepository.findByUserIdAndStatus(userId, "ACTIVE");
    }

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> findByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    public void cancel(Long enrollmentId) {
        Enrollment e = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment", enrollmentId));
        e.setStatus("CANCELLED");
        enrollmentRepository.save(e);
    }
}
