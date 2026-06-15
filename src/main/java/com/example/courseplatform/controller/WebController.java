package com.example.courseplatform.controller;

import com.example.courseplatform.dto.UserRequest;
import com.example.courseplatform.entity.Course;
import com.example.courseplatform.entity.Enrollment;
import com.example.courseplatform.entity.User;
import com.example.courseplatform.repository.UserRepository;
import com.example.courseplatform.service.CourseService;
import com.example.courseplatform.service.EnrollmentService;
import com.example.courseplatform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WebController {
    private final CourseService courseService;
    private final UserService userService;
    private final EnrollmentService enrollmentService;
    private final UserRepository userRepository;

    public WebController(CourseService courseService, UserService userService,
                         EnrollmentService enrollmentService, UserRepository userRepository) {
        this.courseService = courseService;
        this.userService = userService;
        this.enrollmentService = enrollmentService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "courses";
    }

    @GetMapping("/courses/{id}")
    public String courseDetail(@PathVariable Long id, Model model, Authentication auth) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        Enrollment enrollment = null;
        if (auth != null && auth.isAuthenticated()) {
            enrollment = enrollmentService.getEnrollment(auth.getName(), id);
        }
        model.addAttribute("alreadyEnrolled", enrollment != null);
        model.addAttribute("enrollment", enrollment);
        return "course-detail";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute("userRequest") UserRequest request,
                                  BindingResult bindingResult, Model model,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "register";
        try {
            userService.create(request);
            redirectAttributes.addFlashAttribute("success", "Cadastro realizado! Faça login para continuar.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @PostMapping("/enroll/{courseId}")
    public String enroll(@PathVariable Long courseId,
                         @RequestParam(name = "paymentMethod", required = false) String paymentMethod,
                         Authentication auth,
                         RedirectAttributes redirectAttributes) {
        try {
            Enrollment e = enrollmentService.create(auth.getName(), courseId, paymentMethod);
            redirectAttributes.addFlashAttribute("success",
                    "Matrícula confirmada via " + e.getPaymentMethod() + "! Bom estudo. 🎓");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/courses/" + courseId;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        List<Enrollment> enrollments = enrollmentService.findActiveByUserId(user.getId());
        int totalLessons = enrollments.stream()
                .mapToInt(e -> e.getCourse().getLessons().size())
                .sum();
        model.addAttribute("user", user);
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("totalLessons", totalLessons);
        return "dashboard";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
