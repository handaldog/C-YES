package com.cyes.webserver.domain.quiz.dto;

import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QuizCreateResponse {

    private Long quizId;
    private String quizTitle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime quizStartDate;

    @Builder
    public QuizCreateResponse(Long quizId, String title, LocalDateTime quizStartDate) {
        this.quizId = quizId;
        this.quizTitle = title;
        this.quizStartDate = quizStartDate;
    }
    public static QuizCreateResponse of(Quiz quiz){
        return QuizCreateResponse.builder()
                .quizId(quiz.getId())
                .quizTitle(quiz.getTitle())
                .quizStartDate(quiz.getStartDateTime())
                .build();
    }
}
