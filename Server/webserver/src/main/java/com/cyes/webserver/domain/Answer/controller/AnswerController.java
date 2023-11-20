package com.cyes.webserver.domain.Answer.controller;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import com.cyes.webserver.domain.Answer.dto.AnswerSaveControllerRequest;
import com.cyes.webserver.domain.Answer.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
@Slf4j
@Tag(name = "답안",description = "답안에 관한 API")
public class AnswerController {
    private final AnswerService answerService;

    @Operation(summary = "답안 저장", description = "문제에 대한 답안을 저장한다.")
    @PostMapping("")
    public ResponseEntity<AnswerResponse> save(@RequestBody @Valid AnswerSaveControllerRequest answerSaveControllerRequest){
        //Time은 임시로 넣어놓은 것이다.
        AnswerResponse answerResponse = answerService.save(answerSaveControllerRequest.toServiceRequest(), LocalDateTime.now(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(answerResponse);
    }

    @Operation(summary = "답안 조회", description = "특정 유저가 특정 퀴즈에 제출한 답안을 조회한다.\n" +
            "parameter로 memberId, quizId를 넘겨준다")
    @GetMapping("")
    public ResponseEntity<List<AnswerResponse>> findAnswerByMemberIdAndQuizId(
            @NotNull(message = "memberId는 필수 값 입니다.") @RequestParam Long memberId,
            @NotNull(message = "quizId는 필수 값 입니다.") @RequestParam Long quizId) {
        List<AnswerResponse> answerByMemberIdAndQuizId = answerService.findAnswerByMemberIdAndQuizId(memberId, quizId);
        return ResponseEntity.status(HttpStatus.OK).body(answerByMemberIdAndQuizId);
    }
}


