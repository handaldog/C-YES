package com.cyes.webserver.domain.Answer.service;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import com.cyes.webserver.domain.Answer.dto.AnswerSaveServiceRequest;
import com.cyes.webserver.domain.Answer.repository.AnswerRepository;
import com.cyes.webserver.exception.CustomException;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnswerServiceTest {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerRepository answerRepository;

    @AfterEach
    void tearDown() {
        answerRepository.deleteAll();
    }

    @DisplayName("client에서 답안을 보내면 답안을 저장한다.")
    @Test
    void save(){
        //given
        //답안
        AnswerSaveServiceRequest answerRequest = AnswerSaveServiceRequest.builder()
                .memberId(1L)
                .quizId(1L)
                .problemNumber(1)
                .submitContent("답안")
                .build();
        //문제 출제시간
        LocalDateTime publishTime = LocalDateTime.of(2023, 11, 5, 12, 00);
        //답안 제출시간
        LocalDateTime submitTime = LocalDateTime.of(2023, 11, 5, 12, 1);

        //when
        AnswerResponse answerResponse = answerService.save(answerRequest, publishTime, submitTime);


        //then
        assertThat(answerResponse).extracting("memberId", "quizId", "problemNumber", "submitContent", "duringTime")
                .contains(answerRequest.getMemberId(),
                        answerRequest.getQuizId(),
                        answerRequest.getProblemNumber(),
                        answerRequest.getSubmitContent(),
                        Duration.between(publishTime, submitTime).toNanos());
    }
    @DisplayName("같은 퀴즈, 같은 문제에 이미 답안을 제출했다면 예외가 발생한다.")
    @Test
    void saveDuplicatedAnswer(){
        //given
        AnswerSaveServiceRequest answerRequest1 = AnswerSaveServiceRequest.builder().memberId(1L).quizId(1L).problemNumber(1).submitContent("답안1").build();
        AnswerSaveServiceRequest answerRequest2 = AnswerSaveServiceRequest.builder().memberId(1L).quizId(1L).problemNumber(1).submitContent("답안2").build();
        //문제 출제시간
        LocalDateTime publishTime = LocalDateTime.of(2023, 11, 5, 12, 00);
        //답안 제출시간
        LocalDateTime submitTime = LocalDateTime.of(2023, 11, 5, 12, 1);
        answerService.save(answerRequest1,publishTime,submitTime);

        //when //then
        assertThatThrownBy(() -> answerService.save(answerRequest1, publishTime, submitTime))
                .isInstanceOf(CustomException.class)
                .hasMessage("이미 정답을 제출했습니다.");
    }
    @DisplayName("제출시간이 출제시간보다 빠른 정답을 제출하면 예외가 발생한다.")
    @Test
    void saveUnMatchTime(){
        //given
        //답안
        AnswerSaveServiceRequest answerRequest = AnswerSaveServiceRequest.builder().memberId(1L).quizId(1L).problemNumber(1).submitContent("답안").build();
        //문제 출제시간
        LocalDateTime publishTime = LocalDateTime.of(2023, 11, 5, 12, 00);
        //답안 제출시간
        LocalDateTime submitTime = LocalDateTime.of(2023, 11, 5, 12, 00);
        //문제 출제시간
        LocalDateTime publishTime1 = LocalDateTime.of(2023, 11, 5, 12, 00);
        //답안 제출시간
        LocalDateTime submitTime1 = LocalDateTime.of(2023, 11, 5, 11, 59);
        //when//then
        assertThatThrownBy(() -> answerService.save(answerRequest, publishTime, submitTime))
                .isInstanceOf(CustomException.class)
                .hasMessage("답안 제출시간은 문제 출제시간보다 같거나 빠를 수 없습니다.");
        assertThatThrownBy(() -> answerService.save(answerRequest, publishTime1, submitTime1))
                .isInstanceOf(CustomException.class)
                .hasMessage("답안 제출시간은 문제 출제시간보다 같거나 빠를 수 없습니다.");
    }

    @DisplayName("MemberId,QuizId로 답안을 조회하면 문제 순서대로 답안이 조회된다.")
    @Test
    void findAnswerByMemberIdAndQuizId(){
        //given
        AnswerSaveServiceRequest answerRequest1 = AnswerSaveServiceRequest.builder().memberId(1L).quizId(1L).problemNumber(1).submitContent("답안1").build();
        AnswerSaveServiceRequest answerRequest2 = AnswerSaveServiceRequest.builder().memberId(1L).quizId(1L).problemNumber(2).submitContent("답안2").build();
        //문제 출제시간
        LocalDateTime publishTime = LocalDateTime.of(2023, 11, 5, 12, 00);
        //답안 제출시간
        LocalDateTime submitTime = LocalDateTime.of(2023, 11, 5, 12, 1);
        answerService.save(answerRequest2, publishTime, submitTime);
        answerService.save(answerRequest1, publishTime, submitTime);

        //when
        List<AnswerResponse> answerResponseList = answerService.findAnswerByMemberIdAndQuizId(1L, 1L);

        //then
        assertThat(answerResponseList).extracting("memberId","quizId","problemNumber")
                .containsExactly(
                        Tuple.tuple(1L,1L,1)
                        ,Tuple.tuple(1L,1L,2));
    }
//    private 메서드 테스트 하는 방법 알아보기
//    @DisplayName("Answer Entity List를 AnswerResponse List로 변환한다.")
//    @Test
//    void toAnswerResponse(){
//        //given
//        Answer answer1 = Answer.builder().id("1").memberId(1L).quizId(1L).problemNumber(1).submitContent("1").duringTime(1L).build();
//        Answer answer2 = Answer.builder().id("2").memberId(2L).quizId(2L).problemNumber(2).submitContent("2").duringTime(2L).build();
//        AnswerResponse answerResponse1 = AnswerResponse.builder().id("1").memberId(1L).quizId(1L).problemNumber(1).submitContent("1").duringTime(1L).build();
//        AnswerResponse answerResponse2 = AnswerResponse.builder().id("2").memberId(2L).quizId(2L).problemNumber(2).submitContent("2").duringTime(2L).build();
//
//        //when
//        answerService.
//        //then
//    }

}