package com.cyes.webserver.domain.stompSocket.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GradingResultPresentResponse {

    private Long memberId;
    private String nickName;
    private int correctProblemCount;

    @Builder
    public GradingResultPresentResponse(Long memberId, String nickName, int correctProblemCount) {
        this.memberId = memberId;
        this.nickName = nickName;
        this.correctProblemCount = correctProblemCount;
    }

}
