package com.cyes.webserver.domain.Answer.service;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import com.cyes.webserver.domain.Answer.dto.AnswerSaveServiceRequest;
import com.cyes.webserver.domain.Answer.entity.Answer;
import com.cyes.webserver.domain.Answer.repository.AnswerRepository;
import com.cyes.webserver.domain.stompSocket.dto.SubmitRedis;
import com.cyes.webserver.exception.CustomException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AnswerServiceTestByMockBean {
    @Autowired
    private AnswerService answerService;

    @MockBean
    private AnswerRepository answerRepository;

    @DisplayName("Redis에 저장해 놓았던 답안을 한번에 MongoDB에 저장한다.")
    @Test
    void saveAllSubmitRedis(){
        //given
        List<SubmitRedis> submitRedisList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            submitRedisList.add(SubmitRedis.builder().quizId((long) i).memberId((long) i).problemOrder(i).submitContent("답안" + i).duringTime((long) i).build());
        }
        List<Answer> expectedAnswerList = submitRedisList.stream().map(SubmitRedis::toAnswerDocument).collect(Collectors.toList());

        //when
        answerService.saveAllSubmitRedis(submitRedisList);

        //then
        verify(answerRepository,times(1)).saveAll(expectedAnswerList);
    }
}