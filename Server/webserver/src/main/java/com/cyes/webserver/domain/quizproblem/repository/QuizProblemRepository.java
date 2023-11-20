package com.cyes.webserver.domain.quizproblem.repository;

import com.cyes.webserver.domain.quizproblem.entity.QuizProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizProblemRepository extends JpaRepository<QuizProblem,Long>,QuizProblemRepositoryCustom {

    Optional<List<QuizProblem>> findByQuizId(Long quizId);
}
