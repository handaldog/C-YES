package com.cyes.socketserver.stomp.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;


@Data
@Getter
@ToString
public class SubmitRedis {
    Long quizId;
    Long memberId;
    Integer problemOrder;
    String submitContent;
    Long duringTime;

    public SubmitRedis() {
    }

    @Builder
    public SubmitRedis(Long quizId, Long memberId, Integer problemOrder, String submitContent, Long duringTime) {
        this.quizId = quizId;
        this.memberId = memberId;
        this.problemOrder = problemOrder;
        this.submitContent = submitContent;
        this.duringTime = duringTime;
    }

}
