package com.cyes.webserver.domain.Answer.dto;

import com.cyes.webserver.domain.Answer.entity.Answer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerSaveServiceRequest {
    private Long memberId;
    private Long quizId;
    private Integer problemNumber;
    private String submitContent;

    @Builder
    public AnswerSaveServiceRequest(Long memberId, Long quizId, Integer problemNumber, String submitContent) {
        this.memberId = memberId;
        this.quizId = quizId;
        this.problemNumber = problemNumber;
        this.submitContent = submitContent;
    }

    public Answer toEntity(LocalDateTime startTime, LocalDateTime submitTime){
        Duration between = Duration.between(startTime, submitTime);
        Answer answer = Answer.builder()
                .memberId(this.memberId)
                .quizId(this.quizId)
                .problemNumber(this.problemNumber)
                .submitContent(this.submitContent)
                .duringTime(between.toNanos())
                .build();
        return answer;
    }
}
