package com.cyes.webserver.domain.problem.controller;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import com.cyes.webserver.domain.Answer.service.AnswerService;
import com.cyes.webserver.domain.problem.dto.request.MultipleChoiceProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.ShortAnswerProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.TrueOrFalseProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.response.ProblemResponse;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import com.cyes.webserver.domain.problem.service.ProblemService;
import com.cyes.webserver.interceptor.AuthInterceptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProblemController.class)
class ProblemControllerTest {
    @Autowired
    private ProblemController problemController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProblemService problemService;
    @MockBean
    private AuthInterceptor authInterceptor;
    @DisplayName("MultipleChoice Request Dto로 Problem을 저장할 수 있다.")
    @Test
    void saveMultipleChoice() throws Exception {
        //given
        //request를 보낼 Dto를 만든다.
        String question = "question";
        String answer = "answer";
        String[] choices = {"choice1", "choice2", "choice3", "choice4"};
        String description = "description";
        ProblemCategory category = ProblemCategory.DB;
        MultipleChoiceProblemSaveRequest request = MultipleChoiceProblemSaveRequest.builder().question(question).choices(choices).answer(answer).description(description).problemCategory(category).build();

        //MockBean처리한 ProblemService가 반환할 값을 만든다.
        ProblemResponse response = ProblemResponse.builder().build();

        given(problemService.saveMultipleChoice(any(MultipleChoiceProblemSaveRequest.class))).willReturn(response);

        //when//then
        mockMvc.perform(post("/api/problem/multiplechoice").content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
    @DisplayName("Ture Or False Request Dto로 Problem을 저장할 수 있다.")
    @Test
    void saveTrueOrFalse() throws Exception {
        //given
        // given
        String question = "question";
        String answer = "answer";
        String description = "description";
        ProblemCategory category = ProblemCategory.DB;
        TrueOrFalseProblemSaveRequest request = TrueOrFalseProblemSaveRequest.builder().question(question).answer(answer).description(description).problemCategory(category).build();
        ProblemResponse response = ProblemResponse.builder().build();

        given(problemService.saveTrueOrFalse(any(TrueOrFalseProblemSaveRequest.class))).willReturn(response);

        //when//then
        mockMvc.perform(post("/api/problem/trueorfalse").content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
    @DisplayName("Short Answer Request Dto로 Problem을 저장할 수 있다.")
    @Test
    void saveShortAnswer() throws Exception {
        String question = "question";
        String answer = "answer";
        String description = "description";
        ProblemCategory category = ProblemCategory.DB;
        ShortAnswerProblemSaveRequest request = ShortAnswerProblemSaveRequest.builder().question(question).answer(answer).description(description).problemCategory(category).build();


        ProblemResponse response = ProblemResponse.builder().build();

        given(problemService.saveShortAnswer(any(ShortAnswerProblemSaveRequest.class))).willReturn(response);

        //when//then
        mockMvc.perform(post("/api/problem/shortanswer").content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
    @DisplayName("page,size를 받아서 Problem을 조회할 수 있다.")
    @Test
    void findAllProblem() throws Exception {
        //given
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<ProblemResponse> response = new ArrayList<>();

        given(problemService.findAllProblem(any(Pageable.class))).willReturn(new PageImpl<>(response,pageRequest,response.size()));

        //when//then
        mockMvc.perform(get("/api/problem/all").param("size", String.valueOf(2)).param("page", String.valueOf(0)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new PageImpl<>(response, pageRequest, response.size()))));
    }
    @DisplayName("category, type을 선택하고 그에 대한 problem을 조회한다.")
    @Test
    void findProblemByCategoryOrType() throws Exception {
        //given
        ProblemCategory category = ProblemCategory.DB;
        ProblemType type = ProblemType.MULTIPLECHOICE;
        List<ProblemResponse> response = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 2);

        given(problemService.findProblemByCategoryOrType(any(ProblemCategory.class), any(ProblemType.class), any(Pageable.class))).willReturn(new PageImpl<>(response, pageRequest, response.size()));
        //when//then
        mockMvc.perform(get("/api/problem").param("size", String.valueOf(2)).param("page", String.valueOf(0))
                        .param("problemCategory",String.valueOf(category)).param("problemType",String.valueOf(type)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new PageImpl<>(response, pageRequest, response.size()))));
        verify(problemService).findProblemByCategoryOrType(any(ProblemCategory.class), any(ProblemType.class), any(Pageable.class));
    }

    @Test
    @DisplayName("problemcategory를 조회할 수 있다.")
    void findAllProblemCategory() throws Exception {

        // given
        List<ProblemCategory> categoryList = List.of(ProblemCategory.DB, ProblemCategory.DESIGNPATTERN, ProblemCategory.DATASTRUCTURE, ProblemCategory.COMPUTERARCHITECTURE, ProblemCategory.ALGORITHM, ProblemCategory.NETWORK, ProblemCategory.OS);
        // when// then
        mockMvc.perform(get("/api/problem/category"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryList)));
    }
}