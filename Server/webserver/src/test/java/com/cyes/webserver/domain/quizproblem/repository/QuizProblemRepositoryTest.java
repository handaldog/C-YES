package com.cyes.webserver.domain.quizproblem.repository;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.enums.MemberAuthority;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.domain.quiz.repository.QuizRepository;
import com.cyes.webserver.domain.quizproblem.entity.QuizProblem;
import com.cyes.webserver.utils.oauth.enums.OAuthProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizProblemRepositoryTest {
    @Autowired
    private QuizProblemRepository quizProblemRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        quizProblemRepository.deleteAllInBatch();
        quizRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("quizId로 ProblemId 리스트를 조회한다.")
    void findQuizProblems() {
        // given
        Member member1 = Member.builder().memberEmail("email1@123.123").memberNickname("nickname1").memberAuthority(MemberAuthority.USER).oAuthProvider(OAuthProvider.GUEST).nicknameInitialized(false).build();
        memberRepository.save(member1);
        LocalDateTime startDateTime1 = LocalDateTime.of(2023, 5, 5, 5, 5, 5);
        Quiz quiz1 = Quiz.builder().member(member1).title("가쥬아1").startDateTime(startDateTime1).build();
        quizRepository.save(quiz1);

        String problemId1 = "문제1Id";
        String problemId2 = "문제2Id";
        QuizProblem quizProblem1 = QuizProblem.builder().quiz(quiz1).problemId(problemId1).problemOrder(1).build();
        QuizProblem quizProblem2 = QuizProblem.builder().quiz(quiz1).problemId(problemId2).problemOrder(2).build();
        quizProblemRepository.saveAll(List.of(quizProblem1, quizProblem2));

        // when
        List<String> quizProblems = quizProblemRepository.findQuizProblems(quiz1.getId());

        // then
        assertThat(quizProblems).hasSize(2).containsExactlyInAnyOrder(problemId1, problemId2);
    }
}