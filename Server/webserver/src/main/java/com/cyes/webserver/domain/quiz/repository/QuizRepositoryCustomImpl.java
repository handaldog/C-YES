package com.cyes.webserver.domain.quiz.repository;

import com.cyes.webserver.domain.member.enums.MemberAuthority;
import com.cyes.webserver.domain.quiz.entity.QQuiz;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
public class QuizRepositoryCustomImpl implements QuizRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Optional<Quiz> findLiveQuiz(LocalDateTime nowDateTime) {
        QQuiz quiz = QQuiz.quiz;

        LocalDateTime todayEnd = nowDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);

        return Optional.ofNullable(jpaQueryFactory
                .select(quiz)
                .from(quiz)
                .where(isAdmin(quiz).and(isTimeBetween(nowDateTime, quiz, todayEnd)))
                .orderBy(quiz.startDateTime.asc())
                .fetchFirst());
    }

    @Override
    public Optional<List<Quiz>> findGroupQuiz(LocalDateTime nowDateTime) {

        QQuiz quiz = QQuiz.quiz;

       return Optional.ofNullable(jpaQueryFactory
                .select(quiz)
                .from(quiz)
                .where(isUser(quiz).and(isTimeAfter(nowDateTime, quiz)))
                .orderBy(quiz.startDateTime.asc())
                .fetch());
    }

    @Override
    public Optional<List<Quiz>> findByTitle(String keyword,LocalDateTime nowDateTime) {

        QQuiz quiz = QQuiz.quiz;

        // keyword를 포함하는 Quiz 검색
        return Optional.ofNullable(jpaQueryFactory
                .select(quiz)
                .from(quiz)
                .where(isUser(quiz).and(quiz.title.contains(keyword).and(isTimeAfter(nowDateTime,quiz))))
                .fetch());
    }

    private static BooleanExpression isUser(QQuiz quiz) {
        return quiz.member.memberAuthority.eq(MemberAuthority.USER);
    }

    private static BooleanExpression isAdmin(QQuiz quiz) {
        return quiz.member.memberAuthority.eq(MemberAuthority.ADMIN);
    }

    private static BooleanExpression isTimeBetween(LocalDateTime nowDateTime, QQuiz quiz, LocalDateTime todayEnd) {
        return quiz.startDateTime.between(nowDateTime, todayEnd);
    }
    private static BooleanExpression isTimeAfter(LocalDateTime nowDateTime, QQuiz quiz) {
        return quiz.startDateTime.after(nowDateTime);
    }


}
