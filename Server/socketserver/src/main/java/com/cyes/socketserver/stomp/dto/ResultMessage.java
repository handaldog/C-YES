package com.cyes.socketserver.stomp.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultMessage extends SessionMessage{

    private List<GradingResultPresentResponse> gradingResultPresentResponseList;

    @Builder
    public ResultMessage(Long quizId, MessageType type, List<GradingResultPresentResponse> gradingResultPresentResponseList) {
        super(quizId, type);
        this.gradingResultPresentResponseList = gradingResultPresentResponseList;
    }
}
