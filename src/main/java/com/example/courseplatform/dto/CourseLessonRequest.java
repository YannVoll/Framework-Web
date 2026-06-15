package com.example.courseplatform.dto;

import jakarta.validation.constraints.NotBlank;

public class CourseLessonRequest {
    @NotBlank(message = "Título é obrigatório")
    private String title;

    @NotBlank(message = "Conteúdo é obrigatório")
    private String content;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
