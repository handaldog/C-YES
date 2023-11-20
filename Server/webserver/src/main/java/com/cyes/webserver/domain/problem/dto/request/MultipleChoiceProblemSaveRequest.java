package com.cyes.webserver.domain.problem.dto.request;

import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceProblemSaveRequest {
    private String question;
    private String answer;
    private String[] choices;
    private String description;
    private ProblemCategory problemCategory;

    @Builder
    public MultipleChoiceProblemSaveRequest(String question, String answer, String[] choices, String description, ProblemCategory problemCategory) {
        this.question = question;
        this.answer = answer;
        this.choices = choices;
        this.description = description;
        this.problemCategory = problemCategory;
    }
}
