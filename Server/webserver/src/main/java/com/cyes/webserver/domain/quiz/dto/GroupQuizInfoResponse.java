package com.cyes.webserver.domain.quiz.dto;

import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupQuizInfoResponse {

    private Long quizId;
    private String quizTitle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime quizStartDate;
    private ProblemCategory category;
    private ProblemType type;
    private int problemCnt;

    @Builder
    public GroupQuizInfoResponse(Long quizId, String quizTitle, LocalDateTime quizStartDate, ProblemCategory category, ProblemType type, int problemCnt) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.quizStartDate = quizStartDate;
        this.category = category;
        this.type = type;
        this.problemCnt = problemCnt;
    }
}
