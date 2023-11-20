package com.cyes.webserver.domain.quizrank.entity;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quiz_rank")
public class QuizRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_rank_id",nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "quiz_rank", nullable = false)
    private Integer rank;

    @Builder
    public QuizRank(Long id, Member member, Quiz quiz, Integer rank) {
        this.id = id;
        this.member = member;
        this.quiz = quiz;
        this.rank = rank;
    }
}
