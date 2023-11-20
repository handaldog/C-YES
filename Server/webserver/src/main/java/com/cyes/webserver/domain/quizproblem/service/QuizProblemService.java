package com.cyes.webserver.domain.quizproblem.service;

import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.domain.quizproblem.entity.QuizProblem;
import com.cyes.webserver.domain.quizproblem.repository.QuizProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizProblemService {

    private final QuizProblemRepository quizProblemRepository;

    @Transactional
    public void createQuizProblemByQuiz(Quiz quiz, List<String> problemIdList){
        int probOrder = 1;
        // QuizProblem
        for (String problemId : problemIdList) {
            // Dto -> Entity
            QuizProblem quizProblem = QuizProblem.of(quiz, problemId, probOrder++);
            // Insert QuizProblem
            quizProblemRepository.save(quizProblem);
        }
    }
}
