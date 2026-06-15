package com.example.courseplatform.controller;

import com.example.courseplatform.dto.CourseRequest;
import com.example.courseplatform.dto.CourseResponse;
import com.example.courseplatform.dto.CourseLessonRequest;
import com.example.courseplatform.entity.Course;
import com.example.courseplatform.entity.Lesson;
import com.example.courseplatform.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponse> findAll() {
        return courseService.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CourseResponse findById(@PathVariable Long id) {
        return toResponse(courseService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CourseRequest request) {
        Course course = courseService.create(request);
        return ResponseEntity.created(URI.create("/api/courses/" + course.getId())).body(toResponse(course));
    }

    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        return toResponse(courseService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/lessons")
    public CourseResponse addLesson(@PathVariable Long courseId, @Valid @RequestBody CourseLessonRequest request) {
        Lesson lesson = new Lesson(request.getTitle(), request.getContent());
        Course course = courseService.addLesson(courseId, lesson);
        return toResponse(course);
    }

    private CourseResponse toResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getInstructor(),
                course.getPrice(),
                course.isActive(),
                course.getCreatedAt()
        );
    }
}
