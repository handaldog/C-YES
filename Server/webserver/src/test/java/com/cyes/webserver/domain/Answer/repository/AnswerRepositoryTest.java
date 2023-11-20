package com.cyes.webserver.domain.Answer.repository;

import com.cyes.webserver.domain.Answer.entity.Answer;
import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.repository.ProblemRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 메서드 두개 모두 테스트 완료!!
*/
@SpringBootTest
class AnswerRepositoryTest {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        answerRepository.deleteAll();
    }

    @Test
    @DisplayName("memberId, quizId로 Answer를 problemNumber를 정렬 기준으로 조회할 수 있다.")
    void findAnswerByMemberIdAndQuizId() {
        // given
        //더미 답안 생성
        insertAnswer();
        Sort sort = Sort.by(Sort.Order.asc("problem_number"));
        // when
        List<Answer> answerList = answerRepository.findAnswerByMemberIdAndQuizId(1L, 2L, sort);

        // then
        assertThat(answerList).hasSize(4).extracting("memberId", "quizId", "problemNumber")
                .containsExactly(
                        Tuple.tuple(1L, 2L, 1)
                        , Tuple.tuple(1L, 2L, 2)
                        , Tuple.tuple(1L, 2L, 3)
                        , Tuple.tuple(1L, 2L, 4)
                );
    }

    @Test
    @DisplayName("memberId, quizId, problemNumber로 Answer하나를 조회할 수 있다.")
    void findAnswerByMemberIdAndQuizIdAndProblemNumber() {
        // given
        insertAnswer();
        // when
        Answer answer = answerRepository.findAnswerByMemberIdAndQuizIdAndProblemNumber(5L, 3L, 1).get();
        Optional<Answer> answer1 = answerRepository.findAnswerByMemberIdAndQuizIdAndProblemNumber(2L, 3L, 1);
        // then
        assertThat(answer).extracting("memberId", "quizId", "problemNumber").contains(5L, 3L, 1);
        assertThat(answer1).isEmpty();
    }


    private void insertAnswer(){
        Answer build1 = Answer.builder().memberId(1L).quizId(2L).problemNumber(1).submitContent("답1").duringTime(123L).build();
        Answer build2= Answer.builder().memberId(1L).quizId(2L).problemNumber(2).submitContent("답1").duringTime(123L).build();
        Answer build3 = Answer.builder().memberId(1L).quizId(2L).problemNumber(3).submitContent("답1").duringTime(123L).build();
        Answer build4 = Answer.builder().memberId(1L).quizId(2L).problemNumber(4).submitContent("답1").duringTime(123L).build();
        Answer build5 = Answer.builder().memberId(1L).quizId(3L).problemNumber(1).submitContent("답1").duringTime(123L).build();
        Answer build6 = Answer.builder().memberId(5L).quizId(3L).problemNumber(1).submitContent("답1").duringTime(123L).build();
        Answer build7 = Answer.builder().memberId(5L).quizId(4L).problemNumber(1).submitContent("답1").duringTime(123L).build();
        Answer build8 = Answer.builder().memberId(6L).quizId(4L).problemNumber(1).submitContent("답1").duringTime(123L).build();
        Answer build9 = Answer.builder().memberId(6L).quizId(5L).problemNumber(1).submitContent("답1").duringTime(123L).build();
        Answer build10 = Answer.builder().memberId(7L).quizId(5L).problemNumber(1).submitContent("답1").duringTime(123L).build();

        answerRepository.saveAll(List.of(build1, build2, build3, build4, build5, build6, build7, build8, build9, build10));
    }
}