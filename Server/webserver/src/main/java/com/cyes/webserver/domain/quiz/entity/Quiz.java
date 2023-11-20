package com.cyes.webserver.domain.quiz.entity;

import com.cyes.webserver.common.entity.BaseEntity;
import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import com.cyes.webserver.domain.quiz.dto.GroupQuizInfoResponse;
import com.cyes.webserver.domain.quiz.dto.QuizCreateResponse;
import com.cyes.webserver.domain.quiz.dto.QuizInfoResponse;
import com.cyes.webserver.domain.quizproblem.entity.QuizProblem;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "quiz")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Quiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "quiz_title", nullable = false)
    private String title;

    @Column(name = "quiz_start_date", nullable = false)
    private LocalDateTime startDateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz")
    List<QuizProblem> quizProblemList = new ArrayList<>();


    @Builder
    public Quiz(Long id, Member member, String title, LocalDateTime startDateTime) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.startDateTime = startDateTime;
    }


    public QuizInfoResponse toQuizInfoResponse() {

        return QuizInfoResponse.builder()
                .quizId(this.id)
                .quizTitle(this.title)
                .quizStartDate(this.startDateTime)
                .build();
    }

    public QuizCreateResponse toQuizCreateResponse() {

        return QuizCreateResponse.builder()
                .quizId(this.id)
                .quizTitle(this.title)
                .quizStartDate(this.startDateTime)
                .build();
    }

    public GroupQuizInfoResponse toGroupQuizInfoResponse(ProblemCategory category, ProblemType type, int problemCnt) {
        return GroupQuizInfoResponse.builder()
                .quizId(this.id)
                .quizTitle(this.title)
                .quizStartDate(this.startDateTime)
                .category(category)
                .type(type)
                .problemCnt(problemCnt)
                .build();
    }
}
