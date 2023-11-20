package com.cyes.webserver.domain.problem.dto.response;


import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemResponse {
    private String id;
    private String question;
    private String[] choices;
    private int problemOrder;
    private String answer;
    private String description;
    private ProblemCategory category;
    private ProblemType type;

    @Builder
    public ProblemResponse(String id, String question, String[] choices, int problemOrder, String answer, String description, ProblemCategory category, ProblemType type) {
        this.id = id;
        this.question = question;
        this.choices = choices;
        this.problemOrder = problemOrder;
        this.answer = answer;
        this.description = description;
        this.category = category;
        this.type = type;
    }
}
