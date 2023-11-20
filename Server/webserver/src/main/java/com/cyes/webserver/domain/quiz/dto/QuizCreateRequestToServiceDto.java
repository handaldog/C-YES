package com.cyes.webserver.domain.quiz.dto;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.domain.quizproblem.entity.QuizProblem;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Builder
public class QuizCreateRequestToServiceDto {

    private String quizTitle;
    private Long memberId;
    private LocalDateTime quizStartDate;
    private List<String> problemList;

    @Builder
    public QuizCreateRequestToServiceDto(String quizTitle, Long memberId, LocalDateTime quizStartDate, List<String> problemList) {
        this.quizTitle = quizTitle;
        this.memberId = memberId;
        this.quizStartDate = quizStartDate;
        this.problemList = problemList;
    }

    public Quiz toQuizEntity(Member member) {

        return Quiz.builder()
                .title(this.quizTitle)
                .member(member)
                .startDateTime(this.quizStartDate)
                .build();
    }

}
