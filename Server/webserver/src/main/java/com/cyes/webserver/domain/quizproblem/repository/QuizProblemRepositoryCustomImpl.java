package com.cyes.webserver.domain.quizproblem.repository;

import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.domain.quiz.repository.QuizRepositoryCustom;
import com.cyes.webserver.domain.quizproblem.entity.QQuizProblem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizProblemRepositoryCustomImpl implements QuizProblemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findQuizProblems(Long quizId) {

        QQuizProblem qQuizProblem = QQuizProblem.quizProblem;

        return jpaQueryFactory
                .select(qQuizProblem.problemId)
                .from(qQuizProblem)
                .where(qQuizProblem.quiz.id.eq(quizId))
                .orderBy(qQuizProblem.problemOrder.asc())
                .fetch();
    }
}
