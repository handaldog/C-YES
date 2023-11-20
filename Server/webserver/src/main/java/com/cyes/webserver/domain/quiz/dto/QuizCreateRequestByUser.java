package com.cyes.webserver.domain.quiz.dto;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.problem.dto.request.ProblemSaveByUserRequest;
import com.cyes.webserver.domain.problem.entity.ProblemByUser;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizCreateRequestByUser {
    private Long memberId;
    private String quizTitle;
    private LocalDateTime startDateTime;
    private List<ProblemSaveByUserRequest> problemByUserList;

    @Builder
    public QuizCreateRequestByUser(Long memberId, String quizTitle, LocalDateTime startDateTime, List<ProblemSaveByUserRequest> problemByUserList) {
        this.memberId = memberId;
        this.quizTitle = quizTitle;
        this.startDateTime = startDateTime;
        this.problemByUserList = problemByUserList;
    }



    public Quiz toQuizEntity(Member member){
        return Quiz.builder()
                .member(member)
                .title(this.quizTitle)
                .startDateTime(this.startDateTime)
                .build();
    }

}
