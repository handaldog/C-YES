package com.cyes.webserver.domain.quiz.repository;

import com.cyes.webserver.domain.quiz.entity.Quiz;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long>, QuizRepositoryCustom {

}
