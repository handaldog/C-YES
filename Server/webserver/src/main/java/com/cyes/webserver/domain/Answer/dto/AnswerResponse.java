package com.cyes.webserver.domain.Answer.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerResponse {
    private String id;
    private Long memberId;
    private Long quizId;
    private int problemNumber;
    private String submitContent;
    private Long duringTime;

    @Builder
    public AnswerResponse(String id, Long memberId, Long quizId, int problemNumber, String submitContent, Long duringTime) {
        this.id = id;
        this.memberId = memberId;
        this.quizId = quizId;
        this.problemNumber = problemNumber;
        this.submitContent = submitContent;
        this.duringTime = duringTime;
    }
}
