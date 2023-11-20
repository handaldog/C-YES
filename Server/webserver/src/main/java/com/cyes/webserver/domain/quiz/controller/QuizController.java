package com.cyes.webserver.domain.quiz.controller;

import com.cyes.webserver.domain.quiz.dto.*;
import com.cyes.webserver.domain.quiz.service.QuizService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
@Tag(name = "퀴즈", description = "퀴즈에 관한 API")
public class QuizController {

    private final QuizService quizService;
//    private final KafkaTemplate<Object, Object> producer;

    /**
     * 라이브 퀴즈쇼 정보 조회 API
     *
     * @return QuizInfoResponse
     */
    @Operation(summary = "라이브 퀴즈쇼 정보 조회", description = "라이브 퀴즈쇼 정보를 조회하는 메서드이다.")
    @GetMapping("/live/info")
    private ResponseEntity<QuizInfoResponse> getLiveQuizInfo() {

        return ResponseEntity.status(HttpStatus.OK).body(quizService.searchLiveQuiz(LocalDateTime.now()));
    }

    /**
     * 그룹 퀴즈 정보 조회
     *
     * @return List<GroupQuizInfoResponse>
     */
    @Operation(summary = "그룹 퀴즈 정보 조회", description = "그룹 퀴즈 정보 리스트를 조회하는 메서드이다.")
    @GetMapping("/group/info")
    public ResponseEntity<List<GroupQuizInfoResponse>> getGroupQuizInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(quizService.searchGroupQuiz(LocalDateTime.now()));
    }


    /**
     * 그룹 퀴즈 제목 검색
     *
     * @return List<GroupQuizInfoResponse>
     */
    @Operation(summary = "그룹 퀴즈 제목으로 검색", description = "그룹 퀴즈 정보를 제목을 통해 검색한다. \n" +
            "keyword를 json형식으로 요청해야한다.")
    @PostMapping("/group/info/searchByTitle")
    public ResponseEntity<List<GroupQuizInfoResponse>> getGroupQuizInfoByTitle(@RequestBody GroupQuizInfoRequestByTitle groupQuizInfoRequestByTitle) {
        return ResponseEntity.status(HttpStatus.OK).body(quizService.searchGroupByQuizTitle(groupQuizInfoRequestByTitle.getKeyword()));
    }

    /**
     * 라이브 퀴즈쇼 개설 API
     *
     * @param quizCreateRequest
     * @return QuizCreateResponse
     */
    @Operation(summary = "퀴즈 개설", description = "라이브 퀴즈, 그룹 퀴즈를 개설하는 메서드이다.\n" +
            "QuizCreateRequest에는 quizTitle(퀴즈 이름), memberId(멤버 이름), quizStartDate(퀴즈 시작 시간, problemList(문제pk)를 담아서 보내야한다.")
    @PostMapping("/create")
    private ResponseEntity<QuizCreateResponse> createQuiz(@RequestBody QuizCreateRequest quizCreateRequest) throws JsonProcessingException {

        // service로 보내는 Dto로 변환
        QuizCreateRequestToServiceDto serviceDto = quizCreateRequest.toServiceDto();

        // service 호출
        QuizCreateResponse quizCreateResponse = quizService.createQuiz(serviceDto);

        return ResponseEntity.status(HttpStatus.OK).body(quizCreateResponse);
    }

    @Operation(summary = "유저가 만드는 그룹퀴즈를 생성하는 메서드", description = "유저가 그룹 퀴즈를 만들 때 문제를 저장하고 그 문제로 퀴즈를 생성한다.")
    @PostMapping("/create/user")
    public ResponseEntity<QuizCreateResponse> createQuizByUser(@RequestBody QuizCreateRequestByUser quizCreateRequestByUser){
        log.info("quizCreateRequestByUser : {}",quizCreateRequestByUser);
        QuizCreateResponse quizByUser = quizService.createQuizByUser(quizCreateRequestByUser);
        return ResponseEntity.status(HttpStatus.OK).body(quizByUser);
    }

    //
//    @Operation(summary = "카프카 테스트", description = "테스트를 위해 임시로 만든 메서드이다.")
//    @GetMapping("/test")
//    private String testKafka() throws JsonProcessingException {
//        SubmitRedis submitRedis = SubmitRedis.builder()
//                .quizId(1L)
//                .memberId(1L)
//                .problemOrder(1)
//                .submitContent(LocalDateTime.now().toString())
//                .duringTime(123L)
//                .build();
//
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String valueStr = objectMapper.writeValueAsString(submitRedis);
//
//        producer.send("demo_java", valueStr);
//        producer.flush();
//
//        return "확인";
//    }

}

