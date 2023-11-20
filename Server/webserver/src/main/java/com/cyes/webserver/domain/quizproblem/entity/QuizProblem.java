package com.cyes.webserver.domain.quizproblem.entity;

import com.cyes.webserver.domain.quiz.entity.Quiz;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quiz_problem")
@Builder
public class QuizProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_problem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "problem_id", length = 50, nullable = false)
    private String problemId;

    @Column(name = "problem_order", columnDefinition = "smallint")
    private Integer problemOrder;

    @Builder
    public QuizProblem(Long id, Quiz quiz, String problemId, Integer problemOrder) {
        this.id = id;
        this.quiz = quiz;
        this.problemId = problemId;
        this.problemOrder = problemOrder;
    }
    public static QuizProblem of(Quiz quiz, String problemId,int problemNum){
        return QuizProblem.builder()
                .quiz(quiz)
                .problemId(problemId)
                .problemOrder(problemNum)
                .build();
    }
}
