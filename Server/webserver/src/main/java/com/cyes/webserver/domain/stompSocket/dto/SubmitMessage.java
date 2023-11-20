package com.cyes.webserver.domain.stompSocket.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
public class SubmitMessage extends SessionMessage{
    Long memberId;
    Integer problemOrder;
    String submitContent;

    public String createKey() {
        return "submit_" + this.quizId + "_" + this.problemOrder + "_" + this.memberId;
    }

    public SubmitRedis ToSubmitRedis(LocalDateTime startTime,LocalDateTime submitTime) {

        Long duringTime = Duration.between(startTime, submitTime).toNanos();

        return SubmitRedis.builder()
                .quizId(this.quizId)
                .problemOrder(this.problemOrder)
                .memberId(this.memberId)
                .submitContent(this.submitContent)
                .duringTime(duringTime)
                .build();
    }

    @Builder
    public SubmitMessage(Long memberId, Long quizId, Integer problemOrder, String submitContent) {
        this.memberId = memberId;
        this.quizId = quizId;
        this.problemOrder = problemOrder;
        this.submitContent = submitContent;
    }
}
