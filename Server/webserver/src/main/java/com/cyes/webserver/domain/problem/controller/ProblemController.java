package com.cyes.webserver.domain.problem.controller;


import com.cyes.webserver.domain.problem.dto.request.MultipleChoiceProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.response.ProblemResponse;
import com.cyes.webserver.domain.problem.dto.request.ShortAnswerProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.TrueOrFalseProblemSaveRequest;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import com.cyes.webserver.domain.problem.service.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problem")
@Tag(name = "문제",description = "CS문제에 관한 API")
public class ProblemController {

    private final ProblemService problemService;

    @Operation(summary = "객관식 문제등록", description = "객관식 문제를 등록하는 메서드이다.\n" +
            "question에 문제 내용, answer에 답안 내용\n" +
            "choices에 보기가 String 배열로 4개 들어가면 된다.\n"+
            "problemCategory에는 NETWORK, OS, DB, DATASTRUCTURE, ALGORITHM, DESIGNPATTERN, COMPUTERARCHITECTURE 중 하나가 문자열로 들어가야 한다.")
    @PostMapping("/multiplechoice")
    public ResponseEntity<ProblemResponse> saveMultipleChoice(@RequestBody MultipleChoiceProblemSaveRequest multipleChoiceProblemSaveRequest) {
        log.info("multipleChoiceProblemSaveRequest = {}", multipleChoiceProblemSaveRequest);
        ProblemResponse problemResponse = problemService.saveMultipleChoice(multipleChoiceProblemSaveRequest);
        return ResponseEntity.status(HttpStatus.OK).body(problemResponse);
    }

    @Operation(summary = "참,거짓 문제등록", description = "참,거짓 문제를 등록하는 메서드이다.\n" +
            "question에 문제 내용, answer에 답안 내용(True False 둘 중 하나)\n" +
            "problemCategory에는 NETWORK, OS, DB, DATASTRUCTURE, ALGORITHM, DESIGNPATTERN, COMPUTERARCHITECTURE 중 하나가 문자열로 들어가야 한다.")
    @PostMapping("/trueorfalse")
    public ResponseEntity<ProblemResponse> saveTrueOrFalse(@RequestBody TrueOrFalseProblemSaveRequest trueOrFalseProblemSaveRequest){
        log.info("trueOrFalseProblemSaveRequest = {}",trueOrFalseProblemSaveRequest);
        ProblemResponse problemResponse = problemService.saveTrueOrFalse(trueOrFalseProblemSaveRequest);
        return ResponseEntity.status(HttpStatus.OK).body(problemResponse);
    }

    @Operation(summary = "단답형 문제등록", description = "단답형 문제를 등록하는 메서드이다.\n" +
            "question에 문제 내용, answer에 답안 내용\n" +
            "problemCategory에는 NETWORK, OS, DB, DATASTRUCTURE, ALGORITHM, DESIGNPATTERN, COMPUTERARCHITECTURE 중 하나가 문자열로 들어가야 한다.")
    @PostMapping("/shortanswer")
    public ResponseEntity<ProblemResponse> saveShortAnswer(@RequestBody ShortAnswerProblemSaveRequest shortAnswerProblemSaveRequest){
        log.info("shortAnswerProblemSaveRequest = {}",shortAnswerProblemSaveRequest);
        ProblemResponse problemResponse = problemService.saveShortAnswer(shortAnswerProblemSaveRequest);
        return ResponseEntity.status(HttpStatus.OK).body(problemResponse);
    }
    @Operation(summary = "문제 조회", description = "종류에 상관없이 모든 문제를 조회하는 메서드이다.\n" +
            "page,size를 입력해야 한다. page는 0부터 시작하고 size는 한페이지에 있는 값으 갯수를 의미한다.")
    @GetMapping("all")
    public ResponseEntity<Page<ProblemResponse>> findAllProblem(Pageable pageable){
        Page<ProblemResponse> allProblem = problemService.findAllProblem(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allProblem);
    }
    @Operation(summary = "문제 조회", description = "category, type을 선택하고 그에 대한 문제를 조회하는 메서드이다.\n" +
            "Parameter로 problemCategory, problemType을 넣어줘야 한다.\n" +
            "page,size를 입력해야 한다. page는 0부터 시작하고 size는 한페이지에 있는 값으 갯수를 의미한다.")
    @GetMapping("")
    public ResponseEntity<Page<ProblemResponse>> findProblemByCategoryOrType(
            @RequestParam(name = "problemCategory",required = false) ProblemCategory problemCategory,
            @RequestParam(name = "problemType",required = false) ProblemType problemType,
            Pageable pageable){
        log.info("problemCategory = {}",problemCategory);
        log.info("problemType = {}",problemType);
        log.info("pageable = {}",pageable);
        Page<ProblemResponse> problemByCategoryOrType = problemService.findProblemByCategoryOrType(problemCategory, problemType, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(problemByCategoryOrType);
    }
    @Operation(summary = "문제의 카테고리 리스트를 조회하는 메서드")
    @GetMapping("category")
    public ResponseEntity<List<ProblemCategory>> findAllProblemCategory(){
        List<ProblemCategory> categoryList = List.of(ProblemCategory.DB, ProblemCategory.DESIGNPATTERN, ProblemCategory.DATASTRUCTURE, ProblemCategory.COMPUTERARCHITECTURE, ProblemCategory.ALGORITHM, ProblemCategory.NETWORK, ProblemCategory.OS);
        return ResponseEntity.status(HttpStatus.OK).body(categoryList);
    }

    @Operation(summary = "category, type, size로 random problem을 조회하는 메서드")
    @GetMapping("random")
    public ResponseEntity<List<ProblemResponse>> findProblemRandom(
            @RequestParam(name = "category") ProblemCategory category
            ,@RequestParam(name = "type") ProblemType type
            ,@RequestParam(name = "size") int size
    ){
        List<ProblemResponse> problemResponseList = problemService.findProblemByCategoryTypeRandom(category, type, size);
        return ResponseEntity.status(HttpStatus.OK).body(problemResponseList);
    }
}


