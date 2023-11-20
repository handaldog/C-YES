package com.cyes.webserver.domain.adminQuiz.controller;


import com.cyes.webserver.domain.adminQuiz.dto.outNoCheckShortProblemDTO;
import com.cyes.webserver.domain.adminQuiz.service.OpenAIPostRequestService;
import com.cyes.webserver.domain.problem.dto.request.MultipleChoiceProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.ShortAnswerProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.TrueOrFalseProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.response.ProblemResponse;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.service.ProblemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adminproblem")
@Slf4j
@CrossOrigin("*")
@Tag(name = "문제들",description = "단답형 문제 생성에 관한 API")
public class adminShortProblemController {

    private final OpenAIPostRequestService openAIPostRequestService;
    private final ProblemService problemService;


    @Operation(summary = "문제 생성", description = "단답형 문제 생성을 도와주는 문제들")
    @GetMapping("/create/{word}")
    public ResponseEntity<String> createShortProblem(@PathVariable("word") String word) throws JsonProcessingException {
        System.out.println("들어왔니");
        List<String> wordlist = openAIPostRequestService.sendPostword(word);

        String[] newStr = wordlist.get(0).split("\n");
        for (int i=0;i<newStr.length;i++){

            openAIPostRequestService.makeShortProblem(newStr[i], "DESIGNPATTERN");
        }
        return ResponseEntity.status(HttpStatus.OK).body("저장 성공");

    }

    @Operation(summary = "검증 안된 모든 문제 출력", description = "검증 안된 모든 문제 출력")
    @GetMapping("/no-check-all")
    public Page<outNoCheckShortProblemDTO> noCheckProblemAll(Pageable pageable){

        return openAIPostRequestService.outNoCheckProblems(pageable);

    }

    @Operation(summary = "검증된 단답형 문제 넣기", description = "검증된 단답형 문제 넣기")
    @GetMapping("/yes-short-insert")
    public ResponseEntity<ProblemResponse> yesShortInsert(@RequestParam("question") String question,
                                                          @RequestParam("answer") String answer,
                                                          @RequestParam("category") String category,
                                                          @RequestParam("description") String description
    ) {

        System.out.println("단답형 들어옴");

        ProblemCategory problemCategory = ProblemCategory.valueOf(category);

        ShortAnswerProblemSaveRequest shortAnswerProblemRequest = ShortAnswerProblemSaveRequest.builder()
                .question(question)
                .answer(answer)
                .problemCategory(problemCategory)
                .description(description)
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(problemService.saveShortAnswer(shortAnswerProblemRequest));

    }

    @Operation(summary = "검증된 O/X 문제 넣기", description = "검증된 O/X 문제 넣기")
    @GetMapping("/yes-O/X-insert")
    public ResponseEntity<ProblemResponse> yesOXInsert(@RequestParam("question") String question,
                                                          @RequestParam("answer") String answer,
                                                          @RequestParam("category") String category,
                                                            @RequestParam("description") String description

    ) {

        log.info(answer);
        ProblemCategory problemCategory = ProblemCategory.valueOf(category);

        TrueOrFalseProblemSaveRequest trueOrFalseProblemRequest = TrueOrFalseProblemSaveRequest.builder()
                .question(question)
                .answer(answer)
                .problemCategory(problemCategory)
                .description(description)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(problemService.saveTrueOrFalse(trueOrFalseProblemRequest));

    }

    @Operation(summary = "검증된 4지선다 문제 넣기", description = "검증된 4지선다 문제 넣기")
    @PostMapping("/yes-four-select-insert")
    public ResponseEntity<ProblemResponse> yesFourSelectInsert
            (@RequestBody MultipleChoiceProblemSaveRequest multipleChoiceProblemSaveRequest)
    {

        System.out.println("4지선다 들어옴?");
        System.out.println("multipleChoiceProblemSaveRequest = " + multipleChoiceProblemSaveRequest);
//        ProblemCategory problemCategory = ProblemCategory.valueOf(category);

//        MultipleChoiceProblemSaveRequest.builder().problemCategory(problemCategory);

           return ResponseEntity.status(HttpStatus.OK).body(problemService.saveMultipleChoice(multipleChoiceProblemSaveRequest));

    }

    @Operation(summary = "검증 안된 문제 삭제", description = "검증 안된 문제 삭제")
    @GetMapping("/noCheck/delete")
    public ResponseEntity<String> noCheckDelete(@RequestParam("id") String id){

        return ResponseEntity.status(HttpStatus.OK).body(openAIPostRequestService.noCheckDelete(id));

    }



}
