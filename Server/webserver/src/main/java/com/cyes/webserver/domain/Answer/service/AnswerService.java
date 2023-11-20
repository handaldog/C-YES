package com.cyes.webserver.domain.Answer.service;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import com.cyes.webserver.domain.Answer.dto.AnswerSaveServiceRequest;
import com.cyes.webserver.domain.Answer.entity.Answer;
import com.cyes.webserver.domain.Answer.repository.AnswerRepository;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.problem.repository.ProblemRepository;
import com.cyes.webserver.domain.problem.service.ProblemService;
import com.cyes.webserver.domain.quiz.repository.QuizRepository;
import com.cyes.webserver.domain.quizproblem.repository.QuizProblemRepository;
import com.cyes.webserver.domain.stompSocket.dto.SubmitRedis;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService{
    private final MemberRepository memberRepository;
    private final AnswerRepository answerRepository;
    private final QuizRepository quizRepository;
    private final ProblemRepository problemRepository;
    private final QuizProblemRepository quizProblemRepository;
    private final ProblemService problemService;

    /**답안을 제출하는 메서드
     * 같은 문항에 이미 답을 제출했는지 체크하고
     * 제출하지 않았다면 저장한다.
     * 문제 제출 시간과 답안 제출시간을 parameter로 받는다.
    */
    @Transactional
    public AnswerResponse save(AnswerSaveServiceRequest answerSaveServiceRequest, LocalDateTime startTime, LocalDateTime submitTime){
        Long memberId = answerSaveServiceRequest.getMemberId();
        Long quizId = answerSaveServiceRequest.getQuizId();
        Integer problemNumber = answerSaveServiceRequest.getProblemNumber();


        //이미 답을 제출한 적 있는지 확인
        Optional<Answer> findOptionalAnswer = answerRepository.findAnswerByMemberIdAndQuizIdAndProblemNumber(memberId, quizId, problemNumber);
        boolean after = submitTime.isAfter(startTime);

        if (findOptionalAnswer.isPresent()){
            throw new CustomException(CustomExceptionList.ALREADY_SUBMIT);
        }
        if(!after){
            throw new CustomException(CustomExceptionList.SUBMIT_TIME_ERROR);
        }

        //Dto -> Entity
        Answer answer = answerSaveServiceRequest.toEntity(startTime, submitTime);

        //save
        answerRepository.save(answer);

        //Entity -> Dto
        AnswerResponse answerResponse = answer.toAnswerResponse();

        return answerResponse;
    }

    /**
     * submitRedisList를 parameter로 입력받는다.
     * AnswerList로 변환한다.
     * MongoDB에 saveAll한다.
     * @param submitRedisList
     */
    @Transactional
    public void saveAllSubmitRedis(List<SubmitRedis> submitRedisList) {
        List<Answer> answerList = new ArrayList<>();

        for (SubmitRedis submitRedis : submitRedisList) {
            answerList.add(submitRedis.toAnswerDocument());
        }

        answerRepository.saveAll(answerList);
    }

    /**
     * 특정 유저가 특정 퀴즈에 제출한 답안을 반환한다.
    */
    public List<AnswerResponse> findAnswerByMemberIdAndQuizId(Long memberId, Long quizId){
        //problem_number를 기준으로 오름차순 정렬을 해주는 Sort 객체 생성한다.
        Sort sort = Sort.by(Sort.Order.asc("problem_number"));

        //memberId, quizId, Sort를 파라미터로 넘겨준다.
        List<Answer> answerList = answerRepository.findAnswerByMemberIdAndQuizId(memberId, quizId, sort);

        //Response로 바꿔준다.
        List<AnswerResponse> answerResponseList = toAnswerResponse(answerList);

        //반환
        return answerResponseList;
    }

    /**
     * Entity를 Dto로 바꿔주는 private 메서드
    */
    private List<AnswerResponse> toAnswerResponse(List<Answer> answerList){

        List<AnswerResponse> list = new ArrayList<>();

        for (Answer answer : answerList) {
            AnswerResponse answerResponse = answer.toAnswerResponse();
            list.add(answerResponse);
        }
        return list;
    }
}
