package com.cyes.webserver.domain.quizproblem.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizProblemRepositoryCustom {

    List<String> findQuizProblems(Long quizId);
}
