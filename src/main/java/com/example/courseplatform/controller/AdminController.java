package com.example.courseplatform.controller;

import com.example.courseplatform.dto.CourseRequest;
import com.example.courseplatform.dto.CourseLessonRequest;
import com.example.courseplatform.entity.Course;
import com.example.courseplatform.entity.Lesson;
import com.example.courseplatform.service.CourseService;
import com.example.courseplatform.service.EnrollmentService;
import com.example.courseplatform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final UserService userService;

    public AdminController(CourseService courseService, EnrollmentService enrollmentService, UserService userService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.userService = userService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("enrollments", enrollmentService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/courses/new")
    public String newCourseForm(Model model) {
        model.addAttribute("courseRequest", new CourseRequest());
        return "admin/course-form";
    }

    @PostMapping("/courses/new")
    public String createCourse(@Valid @ModelAttribute("courseRequest") CourseRequest request,
                                BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "admin/course-form";
        courseService.create(request);
        ra.addFlashAttribute("success", "Curso criado com sucesso!");
        return "redirect:/admin";
    }

    @GetMapping("/courses/{id}/edit")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        CourseRequest req = new CourseRequest();
        req.setTitle(course.getTitle());
        req.setDescription(course.getDescription());
        req.setInstructor(course.getInstructor());
        req.setPrice(course.getPrice());
        req.setImageUrl(course.getImageUrl());
        model.addAttribute("courseRequest", req);
        model.addAttribute("courseId", id);
        return "admin/course-form";
    }

    @PostMapping("/courses/{id}/edit")
    public String updateCourse(@PathVariable Long id,
                                @Valid @ModelAttribute("courseRequest") CourseRequest request,
                                BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "admin/course-form";
        }
        courseService.update(id, request);
        ra.addFlashAttribute("success", "Curso atualizado com sucesso!");
        return "redirect:/admin";
    }

    @PostMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes ra) {
        courseService.delete(id);
        ra.addFlashAttribute("success", "Curso removido com sucesso!");
        return "redirect:/admin";
    }

    @GetMapping("/courses/{id}/lessons")
    public String lessonsForm(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.findById(id));
        model.addAttribute("lessonRequest", new CourseLessonRequest());
        return "admin/lessons";
    }

    @PostMapping("/courses/{id}/lessons")
    public String addLesson(@PathVariable Long id,
                             @Valid @ModelAttribute("lessonRequest") CourseLessonRequest request,
                             BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("course", courseService.findById(id));
            return "admin/lessons";
        }
        courseService.addLesson(id, new Lesson(request.getTitle(), request.getContent()));
        ra.addFlashAttribute("success", "Aula adicionada!");
        return "redirect:/admin/courses/" + id + "/lessons";
    }

    @GetMapping("/courses/{id}/enrollments")
    public String courseEnrollments(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.findById(id));
        model.addAttribute("enrollments", enrollmentService.findByCourseId(id));
        return "admin/enrollments";
    }
}
