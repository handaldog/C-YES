package com.cyes.webserver.domain.stompSocket.dto;

import com.cyes.webserver.domain.rank.dto.GradingResult;
import lombok.*;

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
