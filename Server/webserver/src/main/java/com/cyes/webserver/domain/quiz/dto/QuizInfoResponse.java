package com.cyes.webserver.domain.quiz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class QuizInfoResponse {

    private Long quizId;
    private String quizTitle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime quizStartDate;

    @Builder
    public QuizInfoResponse(Long quizId, String quizTitle, LocalDateTime quizStartDate) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.quizStartDate = quizStartDate;
    }
}
