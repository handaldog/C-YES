package com.cyes.webserver.domain.problem.dto.request;

import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortAnswerProblemSaveRequest {
    private String question;
    private String answer;
    private String description;
    private ProblemCategory problemCategory;
    @Builder
    public ShortAnswerProblemSaveRequest(String question, String answer, String description, ProblemCategory problemCategory) {
        this.question = question;
        this.answer = answer;
        this.description = description;
        this.problemCategory = problemCategory;
    }
}
