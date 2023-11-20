package com.cyes.webserver.domain.quiz.repository;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.enums.MemberAuthority;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.utils.oauth.enums.OAuthProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuizRepositoryTest {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private MemberRepository memberRepository;
    @AfterEach
    void tearDown() {
        quizRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("오늘 현재 시간 이후 열리는 첫번째 라이브 퀴즈를 조회한다.")
    void findLiveQuiz() {

        // given
        Member member1 = Member.builder().memberEmail("email1@123.123").memberNickname("nickname1").memberAuthority(MemberAuthority.USER).oAuthProvider(OAuthProvider.GUEST).nicknameInitialized(false).build();
        Member member2 = Member.builder().memberEmail("email2@123.123").memberNickname("nickname1").memberAuthority(MemberAuthority.USER).oAuthProvider(OAuthProvider.GUEST).nicknameInitialized(false).build();
        memberRepository.save(member1);
        memberRepository.save(member2);
        LocalDateTime startDateTime1 = LocalDateTime.of(2023, 5, 5, 5, 5, 5);
        LocalDateTime startDateTime2 = LocalDateTime.of(2023, 5, 5, 8, 5, 5);
        Quiz quiz1 = Quiz.builder().member(member1).title("가쥬아1").startDateTime(startDateTime1).build();
        Quiz quiz2 = Quiz.builder().member(member2).title("가쥬아2").startDateTime(startDateTime2).build();
        quizRepository.save(quiz1);
        quizRepository.save(quiz2);

        // when
        Optional<Quiz> liveQuiz1 = quizRepository.findLiveQuiz(LocalDateTime.of(2023, 5, 5, 5, 5));
        Optional<Quiz> liveQuiz2 = quizRepository.findLiveQuiz(LocalDateTime.of(2023, 5, 5, 5, 6));

        // then
        assertThat(liveQuiz1).isNotEmpty();
        assertThat(liveQuiz2).isNotEmpty();
        assertThat(liveQuiz1.get()).extracting(
                quiz -> quiz.getMember().getMemberId()
                ,quiz -> quiz.getTitle()
                ,quiz -> quiz.getStartDateTime()).containsExactly(member1.getMemberId(), quiz1.getTitle() , startDateTime1);
        assertThat(liveQuiz2.get()).extracting(
                quiz -> quiz.getMember().getMemberId()
                ,quiz -> quiz.getTitle()
                ,quiz -> quiz.getStartDateTime()).containsExactly(member2.getMemberId(), quiz2.getTitle(), startDateTime2);
    }
}