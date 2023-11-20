package com.cyes.webserver.domain.quizproblem.service;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.enums.MemberAuthority;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.quiz.dto.QuizCreateRequestToServiceDto;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.domain.quiz.repository.QuizRepository;
import com.cyes.webserver.domain.quizproblem.entity.QuizProblem;
import com.cyes.webserver.domain.quizproblem.repository.QuizProblemRepository;
import com.cyes.webserver.utils.oauth.enums.OAuthProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class QuizProblemServiceTest {

    @Autowired
    private QuizProblemService quizProblemService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private QuizRepository quizRepository;
    @MockBean
    private QuizProblemRepository quizProblemRepository;

    @AfterEach
    void tearDown() {
        quizProblemRepository.deleteAllInBatch();
        quizRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("Quiz Entity와 problemId List로 Quiz에 해당하는 QuizProblem Entity를 저장한다.")
    @Test
    void createQuizProblemByQuiz(){
        //given
        //Member 생성
        Member member = Member.builder().memberEmail("email@email.com").memberNickname("nickname1").memberAuthority(MemberAuthority.ADMIN).oAuthProvider(OAuthProvider.GUEST).nicknameInitialized(false).build();
        memberRepository.save(member);
        //현재시간
        LocalDateTime now = LocalDateTime.now();
        Quiz quiz = Quiz.builder().member(member).title("title").startDateTime(now).build();
        quizRepository.save(quiz);
        List<String> problemIdList = List.of("problemId1", "problemId");

        //when
        quizProblemService.createQuizProblemByQuiz(quiz, problemIdList);

        //then
        verify(quizProblemRepository,times(problemIdList.size())).save(any(QuizProblem.class));

    }
}