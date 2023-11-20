package com.cyes.webserver.domain.quizrank.repository;

import com.cyes.webserver.domain.quizrank.entity.QuizRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRankRepository extends JpaRepository<QuizRank, Long>{
}
