package com.cyes.webserver.domain.stompSocket.service.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AnswerServiceTest {
    @Autowired
    private AnswerService answerService;


//    @DisplayName("channelTopic으로 문제의 정답인 AnswerMessage를 publish한다.")
//    @Test
//    void sendAnswer(){
//        //given
//        Long quizId = 1L;
//        ProblemResponse problemResponse = ProblemResponse.builder().id("problemId").contentResponse(ProblemContentResponse.builder().answer("답").build()).problemOrder(1).category("ALGORITHM").type("MULTIPLECHOICE").build();
//
//        //when
//
//        answerService.sendAnswer(quizId,problemResponse);
//
//        //then
//        verify(redisTemplate,times(1)).convertAndSend(anyString(),any(AnswerMessage.class));
//    }
}