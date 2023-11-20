package com.cyes.webserver.domain.quiz.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
public class QuizCreateRequest {

    private String quizTitle;
    private Long memberId;
    private LocalDateTime quizStartDate;
    private List<String> problemList;

    public QuizCreateRequestToServiceDto toServiceDto() {

        return QuizCreateRequestToServiceDto.builder()
                .quizTitle(this.quizTitle)
                .memberId(this.memberId)
                .quizStartDate(this.quizStartDate)
                .problemList(this.problemList)
                .build();

    }
}
