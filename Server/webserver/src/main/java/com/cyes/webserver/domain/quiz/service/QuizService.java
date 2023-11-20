package com.cyes.webserver.domain.quiz.service;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.enums.MemberAuthority;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.problem.dto.request.ProblemSaveByUserRequest;
import com.cyes.webserver.domain.problem.entity.ProblemByUser;
import com.cyes.webserver.domain.problem.repository.ProblemByUserRepository;
import com.cyes.webserver.domain.problem.repository.ProblemRepository;
import com.cyes.webserver.domain.problem.service.ProblemService;
import com.cyes.webserver.domain.quiz.dto.*;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.domain.quiz.repository.QuizRepository;
import com.cyes.webserver.domain.quizproblem.entity.QuizProblem;
import com.cyes.webserver.domain.quizproblem.repository.QuizProblemRepository;
import com.cyes.webserver.domain.quizproblem.service.QuizProblemService;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import com.cyes.webserver.redis.service.ScheduleReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizProblemRepository quizProblemRepository;
    private final MemberRepository memberRepository;
    private final ScheduleReserveService scheduleReserveService;
    private final QuizProblemService quizProblemService;
    private final ProblemService problemService;
    private final ProblemRepository problemRepository;

    private final ProblemByUserRepository problemByUserRepository;


    /**
     * 퀴즈 정보 조회
     * @param now
     * @return QuizInfoResponse
     */
    public QuizInfoResponse searchLiveQuiz(LocalDateTime now) {

        // 가장 최근에 생성된 라이브 퀴즈쇼 조회
        Quiz quiz = quizRepository.findLiveQuiz(now).orElse(null);

        QuizInfoResponse quizInfoResponse;

        if (quiz == null) {
            quizInfoResponse = QuizInfoResponse.builder()
                    .quizId(-1L)
                    .quizTitle("")
                    .quizStartDate(LocalDateTime.now())
                    .build();
        } else {
            quizInfoResponse = quiz.toQuizInfoResponse();
        }
        // Entity -> Dto

        return quizInfoResponse;
    }

    /**
     * 그룹 퀴즈 정보 조회
     *
     * @param now
     * @return List<GroupQuizInfoResponse>
     */
    public List<GroupQuizInfoResponse> searchGroupQuiz(LocalDateTime now) {


        // 퀴즈를 만든 사람이 일반 유저인 퀴즈 조회
        List<Quiz> quizList = quizRepository.findGroupQuiz(now).orElseThrow(() -> new CustomException(CustomExceptionList.QUIZ_NOT_FOUND_ERROR));


        // Entirty -> Dto
        List<GroupQuizInfoResponse> responseList = new ArrayList<>();
        for (Quiz quiz : quizList) {
            // 그 퀴즈의 문제 하나를 본다. (퀴즈 유형, 문제 과목 검색을 위해서)
            QuizProblem quizProblem = quiz.getQuizProblemList().get(0);
            int problemCnt = quiz.getQuizProblemList().size();

            ProblemByUser problemByUser = problemByUserRepository.findById(quizProblem.getProblemId()).orElseThrow(() -> new CustomException(CustomExceptionList.PROBLEM_NOT_FOUND_ERROR));
            responseList.add(quiz.toGroupQuizInfoResponse(problemByUser.getCategory(), problemByUser.getType(), problemCnt));

        }

        return responseList;

    }

    /**
     * 제목으로 그룹 퀴즈를 검색한다.
     *
     * @param keyword
     * @return List<GroupQuizInfoResponse>
     */
    public List<GroupQuizInfoResponse> searchGroupByQuizTitle(String keyword) {

        // keyword를 제목으로 포함하고, 퀴즈리스트 조회
        List<Quiz> quizList = quizRepository.findByTitle(keyword, LocalDateTime.now()).orElseThrow(() -> new CustomException(CustomExceptionList.QUIZ_NOT_FOUND_ERROR));

        // Entity -> Dto
        List<GroupQuizInfoResponse> responseList = new ArrayList<>();
        for (Quiz quiz : quizList) {

            // 그 퀴즈의 문제 하나를 본다. (퀴즈 유형, 문제 과목 검색을 위해서)
            QuizProblem quizProblem = quiz.getQuizProblemList().get(0);
            int problemCnt = quiz.getQuizProblemList().size();

            ProblemByUser problemByUser = problemByUserRepository.findById(quizProblem.getProblemId()).orElseThrow(() -> new CustomException(CustomExceptionList.PROBLEM_NOT_FOUND_ERROR));
            responseList.add(quiz.toGroupQuizInfoResponse(problemByUser.getCategory(), problemByUser.getType(), problemCnt));

        }
        return responseList;
    }


    /**
     * 라이브 퀴즈 개설
     * @param dto
     * @return
     */
    public QuizCreateResponse createQuiz(QuizCreateRequestToServiceDto dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new CustomException(CustomExceptionList.MEMBER_NOT_FOUND_ERROR));

        // Dto -> Entity
        Quiz quiz = dto.toQuizEntity(member);

        // Insert Quiz
        quizRepository.save(quiz);

        // Insert QuizProblem
        quizProblemService.createQuizProblemByQuiz(quiz, dto.getProblemList());
        
        // Redis에 퀴즈 시작 트리거 저장
        scheduleReserveService.saveQuiz(quiz.getId(), quiz.getStartDateTime());

        // Entity -> Response Dto
        return quiz.toQuizCreateResponse();
    }


    /**
     * 유저가 만드는 퀴즈 저장
     *
     * @param quizCreateRequestByUser
     */
    public QuizCreateResponse createQuizByUser(QuizCreateRequestByUser quizCreateRequestByUser) {
        //유저찾기
        Member member = memberRepository.findById(quizCreateRequestByUser.getMemberId())
                .orElseThrow(() -> new CustomException(CustomExceptionList.MEMBER_NOT_FOUND_ERROR));

        if(member.getMemberAuthority() == MemberAuthority.ADMIN){
            throw new CustomException(CustomExceptionList.ADMIN_DOES_NOT_CREATE_GROUP_QUIZ);
        }

        //문제저장
        List<ProblemSaveByUserRequest> problemByUserList = quizCreateRequestByUser.getProblemByUserList();

        //문제 pk list
        List<String> problemIdList = problemService.saveProblemByUserAll(problemByUserList);

        //퀴즈저장
        Quiz quizEntity = quizCreateRequestByUser.toQuizEntity(member);
        quizRepository.save(quizEntity);

        //퀴즈 문제 연관관계 저장
        quizProblemService.createQuizProblemByQuiz(quizEntity, problemIdList);

        scheduleReserveService.saveUserQuiz(quizEntity.getId(), quizEntity.getStartDateTime());

        return QuizCreateResponse.of(quizEntity);
    }
}
