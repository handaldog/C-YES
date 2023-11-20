package com.cyes.webserver.domain.problem.service;

import com.cyes.webserver.domain.problem.dto.request.MultipleChoiceProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.ProblemSaveByUserRequest;
import com.cyes.webserver.domain.problem.dto.request.ShortAnswerProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.TrueOrFalseProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.response.ProblemResponse;
import com.cyes.webserver.domain.problem.entity.*;
import com.cyes.webserver.domain.problem.repository.ProblemByUserRepository;
import com.cyes.webserver.domain.problem.repository.ProblemRepository;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final ProblemByUserRepository problemByUserRepository;

    //객관식 문제 저장
    @Transactional
    public ProblemResponse saveMultipleChoice(MultipleChoiceProblemSaveRequest multipleChoiceProblemSaveRequest){
        log.info("multipleChoiceProblemSaveRequest = {}",multipleChoiceProblemSaveRequest);

        System.out.println("들어옴?222");
        //Dto -> Document
        Problem multipleChoice = Problem.createMultipleChoice(multipleChoiceProblemSaveRequest);

        //Problem DB 저장
        problemRepository.save(multipleChoice);

        //Entity -> Dto
        ProblemResponse problemResponse = multipleChoice.toProblemResponse(0);

        return problemResponse;
    }

    //단답형 문제 저장
    @Transactional
    public ProblemResponse saveShortAnswer(ShortAnswerProblemSaveRequest shortAnswerProblemSaveRequest){
        log.info("shortAnswerProblemSaveRequest = {}",shortAnswerProblemSaveRequest);
        //Dto -> Entity
        Problem shortAnswer = Problem.createShortAnswer(shortAnswerProblemSaveRequest);

        //Problem DB 저장
        problemRepository.save(shortAnswer);

        //Entity -> Dto
        ProblemResponse problemResponse = shortAnswer.toProblemResponse(0);
        return problemResponse;
    }

    //오엑스 문제 저장
    @Transactional
    public ProblemResponse saveTrueOrFalse(TrueOrFalseProblemSaveRequest trueOrFalseProblemSaveRequest){
        log.info("trueOrFalseProblemSaveRequest = {}",trueOrFalseProblemSaveRequest);
        //Dto -> Entity
        Problem trueOrFalse = Problem.createTrueOrFalse(trueOrFalseProblemSaveRequest);

        //Problem DB 저장
        problemRepository.save(trueOrFalse);

        //Entity -> Dto
        ProblemResponse problemResponse = trueOrFalse.toProblemResponse(0);
        return problemResponse;
    }
    //유저가 만든 퀴즈의 문제 저장하는 메서드
    public List<String> saveProblemByUserAll(List<ProblemSaveByUserRequest> problemSaveByUserRequestList){
        //problem id list 반환 할 List
        List<String> problemIdList = new ArrayList<>();

        for (ProblemSaveByUserRequest problemSaveByUserRequest : problemSaveByUserRequestList) {

            //Dto -> Document
            ProblemByUser problemDocument = problemSaveByUserRequest.toDocument();

            //Document save
            problemByUserRepository.save(problemDocument);

            //get problem Id
            String problemId = problemDocument.getId();

            //add problemId to problemList
            problemIdList.add(problemId);
        }
        return problemIdList;
    }
    //문제 전체 조회 - 페이지네이션
    public Page<ProblemResponse> findAllProblem(Pageable pageable){

        //페이지네이션 적용해서 select
        Page<Problem> problemPage = problemRepository.findAll(pageable);

        //getContent 이용해서 문제 리스트 가져옴
        List<Problem> problemList = problemPage.getContent();

        //problemListResponseList로 변환
        List<ProblemResponse> problemResponseList = toProblemResponseList(problemList);

        return new PageImpl<>(problemResponseList, problemPage.getPageable(), problemPage.getTotalElements());
    }

    //catetory,type으로 문제 조회 - 페이지네이션
    public Page<ProblemResponse> findProblemByCategoryOrType(ProblemCategory problemCategory, ProblemType problemType, Pageable pageable) {
        Page<Problem> problemPage = null;
        //선택조건이 하나도 없다면
        if (problemCategory == null && problemType == null) {
            throw new CustomException(CustomExceptionList.CATEGORY_OR_TYPE_MUST_REQUIRED);
        } else if (problemCategory != null && problemType != null) {
            problemPage = problemRepository.findProblemByCategoryAndType(problemCategory, problemType, pageable);
        } else if (problemType != null) {
            problemPage = problemRepository.findProblemByType(problemType, pageable);
        } else {
            problemPage = problemRepository.findProblemByCategory(problemCategory, pageable);
        }

        //getContent 이용해서 문제 리스트 가져옴
        List<Problem> problemList = problemPage.getContent();

        //problemListResponseList로 변환
        List<ProblemResponse> problemResponseList = toProblemResponseList(problemList);

        return new PageImpl<>(problemResponseList, problemPage.getPageable(), problemPage.getTotalElements());
    }

    /**
     * category, type, size를 입력받아서 조건에 맞는
     * randomproblem을 size만큼 반환한다.
     * @param problemCategory
     * @param problemType
     * @param size
     * @return List<ProblemResponse></>
     */
    public List<ProblemResponse> findProblemByCategoryTypeRandom(ProblemCategory problemCategory, ProblemType problemType, int size){
        List<Problem> problemList = problemRepository.findProblemByCategoryAndTypeRandom(problemCategory, problemType, size);
        return toProblemResponseList(problemList);
    }

    /*
    * 특정 퀴즈의 문제pk를 list로 넘겨주면
    * 그에 해당하는 problem들을 반환한다.
    * */
     public List<ProblemResponse> findAllProblemByQuiz(List<String> problemIdList){
         Iterable<Problem> findProblems = problemRepository.findAllById(problemIdList);
         Map<String, Problem> map = new HashMap<>();
         for (Problem findProblem : findProblems) {
             map.put(findProblem.getId(), findProblem);
         }

         List<ProblemResponse> problemResponseList = toProblemResponseList(
                 problemIdList.stream().map(map::get).collect(Collectors.toList())
         );
         return problemResponseList;
     }

    /*
     * 특정 유저 퀴즈의 문제pk를 list로 넘겨주면
     * 그에 해당하는 problem들을 반환한다.
     * */
    public List<ProblemResponse> findAllProblemByUserByQuiz(List<String> problemIdList){
        Iterable<ProblemByUser> findProblems = problemByUserRepository.findAllById(problemIdList);
        Map<String, ProblemByUser> map = new HashMap<>();
        for (ProblemByUser findProblem : findProblems) {
            map.put(findProblem.getId(), findProblem);
        }

        List<ProblemResponse> problemResponseList = toProblemByUserResponseList(
                problemIdList.stream().map(map::get).collect(Collectors.toList())
        );
        return problemResponseList;
    }


    //문제를 삭제한다.
    @Transactional
    public void deleteProblem(String id) {
        problemRepository.deleteById(id);
    }

    //문제 랜덤으로 정해진 갯수만큼 조회
    //문제 갯수, 카테고리, 입력받아서 랜덤으로 조회
    private List<ProblemResponse> toProblemResponseList(Iterable<Problem> problemList){
        List<ProblemResponse> list = new ArrayList<>();
        int problemOrder = 1;
        for (Problem problem : problemList) {
            list.add(problem.toProblemResponse(problemOrder++));
        }
        return list;
    }

    private List<ProblemResponse> toProblemByUserResponseList(Iterable<ProblemByUser> problemList){
        List<ProblemResponse> list = new ArrayList<>();
        int problemOrder = 1;
        for (ProblemByUser problem : problemList) {
            list.add(problem.toProblemResponse(problemOrder++));
        }
        return list;
    }

}
