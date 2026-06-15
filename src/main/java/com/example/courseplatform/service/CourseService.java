package com.example.courseplatform.service;

import com.example.courseplatform.dto.CourseRequest;
import com.example.courseplatform.entity.Course;
import com.example.courseplatform.entity.Lesson;
import com.example.courseplatform.exception.ResourceNotFoundException;
import com.example.courseplatform.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));
    }

    public Course create(CourseRequest request) {
        Course course = new Course(request.getTitle(), request.getDescription(), request.getInstructor(), request.getPrice());
        if (request.getImageUrl() != null) course.setImageUrl(request.getImageUrl());
        return courseRepository.save(course);
    }

    public Course update(Long id, CourseRequest request) {
        Course course = findById(id);
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setInstructor(request.getInstructor());
        course.setPrice(request.getPrice());
        if (request.getImageUrl() != null) course.setImageUrl(request.getImageUrl());
        return courseRepository.save(course);
    }

    public void delete(Long id) {
        courseRepository.delete(findById(id));
    }

    public Course addLesson(Long courseId, Lesson lesson) {
        Course course = findById(courseId);
        course.addLesson(lesson);
        return courseRepository.save(course);
    }
}
