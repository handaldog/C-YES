package com.cyes.webserver.domain.quiz.repository;

import com.cyes.webserver.domain.quiz.entity.Quiz;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepositoryCustom {

    Optional<Quiz> findLiveQuiz(LocalDateTime nowDateTime);

    Optional<List<Quiz>> findGroupQuiz(LocalDateTime nowDateTime);

    Optional<List<Quiz>> findByTitle(String keyword, LocalDateTime nowDateTime);
}
