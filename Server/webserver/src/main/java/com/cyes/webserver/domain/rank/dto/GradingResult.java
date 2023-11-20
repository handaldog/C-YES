package com.cyes.webserver.domain.rank.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GradingResult implements Comparable<GradingResult>{
    private Long memberId;
    //맞은 문제
    private int correctProblemCount;
    //맞은 문제를 푸는데 걸린 시간
    private long correctProblemDuringTime;

    @Builder
    public GradingResult(Long memberId, int correctProblemCount, long correctProblemDuringTime) {
        this.memberId = memberId;
        this.correctProblemCount = correctProblemCount;
        this.correctProblemDuringTime = correctProblemDuringTime;
    }




    @Override
    public int compareTo(GradingResult gradingResult) {
        return Integer.compare(this.correctProblemCount, gradingResult.correctProblemCount) != 0
                ? -Integer.compare(this.correctProblemCount, gradingResult.correctProblemCount)
                : Long.compare(this.correctProblemDuringTime, gradingResult.correctProblemDuringTime);
    }

    public void addCorrectCount(){
        this.correctProblemCount++;
    }
    public void addDuringTime(long duringTime){
        this.correctProblemDuringTime += duringTime;
    }

    @Override
    public String toString() {
        return "GradingResult{" +
                "memberId=" + memberId +
                ", correctProblemCount=" + correctProblemCount +
                ", correctProblemDuringTime=" + correctProblemDuringTime +
                '}';
    }
}
