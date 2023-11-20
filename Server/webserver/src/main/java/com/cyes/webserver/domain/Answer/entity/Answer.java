package com.cyes.webserver.domain.Answer.entity;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.naming.Name;
import java.util.Objects;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    //pk
    @Id
    private String id;

    //답안 제출자
    @Indexed
    @Field(name = "member_id")
    private Long memberId;

    //quiz pk
    @Indexed
    @Field(name = "quiz_id")
    private Long quizId;

    //퀴즈 내에서 문제 번호
    @Field(name = "problem_number")
    private int problemNumber;

    //제출 답안
    @Field(name = "submit_content")
    private String submitContent;

    //문제 제출시간과 답안 제출시간의 차이
    @Field(name = "during_time")
    private Long duringTime;

    @Builder
    public Answer(String id, Long memberId, Long quizId, int problemNumber, String submitContent, Long duringTime) {
        this.id = id;
        this.memberId = memberId;
        this.quizId = quizId;
        this.problemNumber = problemNumber;
        this.submitContent = submitContent;
        this.duringTime = duringTime;
    }

    public AnswerResponse toAnswerResponse(){
        AnswerResponse answerResponse = AnswerResponse.builder()
                .id(this.id)
                .memberId(this.memberId)
                .quizId(this.quizId)
                .problemNumber(this.problemNumber)
                .submitContent(this.submitContent)
                .duringTime(this.duringTime)
                .build();
        return answerResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return getProblemNumber() == answer.getProblemNumber() && Objects.equals(getId(), answer.getId()) && Objects.equals(getMemberId(), answer.getMemberId()) && Objects.equals(getQuizId(), answer.getQuizId()) && Objects.equals(getSubmitContent(), answer.getSubmitContent()) && Objects.equals(getDuringTime(), answer.getDuringTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMemberId(), getQuizId(), getProblemNumber(), getSubmitContent(), getDuringTime());
    }
}
