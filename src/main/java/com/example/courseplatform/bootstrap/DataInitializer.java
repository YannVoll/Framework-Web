package com.example.courseplatform.bootstrap;

import com.example.courseplatform.entity.Course;
import com.example.courseplatform.entity.Enrollment;
import com.example.courseplatform.entity.Lesson;
import com.example.courseplatform.entity.User;
import com.example.courseplatform.repository.CourseRepository;
import com.example.courseplatform.repository.EnrollmentRepository;
import com.example.courseplatform.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CourseRepository courseRepository, UserRepository userRepository,
                           EnrollmentRepository enrollmentRepository, PasswordEncoder passwordEncoder) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        User admin = new User("Administrador", "admin@edulab.com", passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        userRepository.save(admin);

        User student = new User("Yan Vollrath", "vollbyan@gmail.com", passwordEncoder.encode("senha123"));
        userRepository.save(student);

        userRepository.save(new User("Bruno Lima", "bruno.lima@example.com", passwordEncoder.encode("segredo456")));

        Course javaCourse = new Course("Java para Iniciantes", "Aprenda os fundamentos de Java e Spring Boot do zero ao avançado.", "Maria Silva", 199.90);
        javaCourse.setImageUrl("/images/java-course.svg");
        javaCourse.addLesson(new Lesson("Introdução ao Java", "Visão geral do Java e ambiente de desenvolvimento."));
        javaCourse.addLesson(new Lesson("Entidades e Repositórios", "Criação de entidades JPA e repositórios Spring Data."));
        javaCourse.addLesson(new Lesson("Spring Boot na prática", "Criando sua primeira aplicação Spring Boot completa."));
        courseRepository.save(javaCourse);

        Course webCourse = new Course("Desenvolvimento Web com Spring", "Crie aplicações web modernas com Spring Boot e Thymeleaf.", "João Pereira", 249.90);
        webCourse.setImageUrl("/images/web-course.svg");
        webCourse.addLesson(new Lesson("Spring MVC", "Entendendo controllers, views e rotas."));
        webCourse.addLesson(new Lesson("Validação e APIs REST", "Valide dados de formulários e crie APIs RESTful."));
        webCourse.addLesson(new Lesson("Segurança com Spring Security", "Autenticação e autorização na prática."));
        courseRepository.save(webCourse);

        // Pré-matricula o aluno de teste no curso de Java (para a Minha Área já ter conteúdo)
        enrollmentRepository.save(new Enrollment(student, javaCourse, "ACTIVE"));
    }
}
