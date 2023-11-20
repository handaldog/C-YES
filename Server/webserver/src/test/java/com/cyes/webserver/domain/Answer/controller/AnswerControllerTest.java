package com.cyes.webserver.domain.Answer.controller;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import com.cyes.webserver.domain.Answer.dto.AnswerSaveControllerRequest;
import com.cyes.webserver.domain.Answer.service.AnswerService;
import com.cyes.webserver.interceptor.AuthInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AnswerController.class)
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private AuthInterceptor authInterceptor;
//    private AuthTokensGenerator authTokensGenerator;
    @Test
    @DisplayName("문제에 대한 답안을 저장한다.")
    void save() throws Exception {
        // given
        //요청을 보낼 requst를 만든다.
        AnswerSaveControllerRequest request = AnswerSaveControllerRequest.builder().memberId(1L).quizId(1L).problemNumber(1).submitContent("답안").build();
        //MockBean처리한 answerService가 반환할 Dto를 준비한다.
        AnswerResponse answerResponse = AnswerResponse.builder().build();
        //answerService가 save할 때 반환할 값을 설정한다.
        given(answerService.save(any(), any(), any())).willReturn(answerResponse);

        // when// then
        mockMvc.perform(post("/api/answer").content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(answerResponse)));
    }
    @Test
    @DisplayName("memberId를 입력하지 않으면 bind error가 발생한다.")
    void saveWithOutMemberId() throws Exception {
        // given
        //요청을 보낼 requst를 만든다.
        AnswerSaveControllerRequest request = AnswerSaveControllerRequest.builder().quizId(1L).problemNumber(1).submitContent("답안").build();
        // when// then
        mockMvc.perform(post("/api/answer").content(objectMapper.writeValueAsString(request)).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }@Test
    @DisplayName("quizId를 입력하지 않으면 bind error가 발생한다.")
    void saveWithOutQuizId() throws Exception {
        // given
        //요청을 보낼 requst를 만든다.
        AnswerSaveControllerRequest request = AnswerSaveControllerRequest.builder().memberId(1L).problemNumber(1).submitContent("답안").build();
        // when// then
        mockMvc.perform(post("/api/answer").content(objectMapper.writeValueAsString(request)).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("problemNumber를 입력하지 않으면 bind error가 발생한다.")
    void saveWithOutproblemNumber() throws Exception {
        // given
        //요청을 보낼 requst를 만든다.
        AnswerSaveControllerRequest request = AnswerSaveControllerRequest.builder().memberId(1L).problemNumber(0).quizId(1L).submitContent("답안").build();
        // when// then
        mockMvc.perform(post("/api/answer").content(objectMapper.writeValueAsString(request)).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("특정 유저가 특정 퀴즈에 제출한 답안을 조회한다.")
    void findAnswerByMemberIdAndQuizId() throws Exception {
        // given
        Long memberId =1L;
        Long quizId = 1L;
        List<AnswerResponse> responseList = List.of();
        given(answerService.findAnswerByMemberIdAndQuizId(memberId, quizId)).willReturn(responseList);

        // when // then
        mockMvc.perform(get("/api/answer").param("memberId", String.valueOf(memberId)).param("quizId", String.valueOf(quizId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseList)));
    }
    @Test
    @DisplayName("memberId를 parameter로 넘겨주지 않으면 예외가 발생한다.")
    void findWithOutMemberId() throws Exception {
        Long memberId =null;
        Long quizId = 1L;
        List<AnswerResponse> responseList = List.of();
        given(answerService.findAnswerByMemberIdAndQuizId(memberId, quizId)).willReturn(responseList);

        // when // then
        mockMvc.perform(get("/api/answer").param("memberId", String.valueOf(memberId)).param("quizId", String.valueOf(quizId)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
    @Test
    @DisplayName("quizId를 parameter로 넘겨주지 않으면 예외가 발생한다.")
    void findWithOutQuizId() throws Exception {
        Long memberId = 1L;
        Long quizId = null;
        List<AnswerResponse> responseList = List.of();
        given(answerService.findAnswerByMemberIdAndQuizId(memberId, quizId)).willReturn(responseList);

        // when // then
        mockMvc.perform(get("/api/answer").param("memberId", String.valueOf(memberId)).param("quizId", String.valueOf(quizId)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}