package com.cyes.webserver.domain.quiz.service;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.enums.MemberAuthority;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.quiz.dto.QuizCreateRequestToServiceDto;
import com.cyes.webserver.domain.quiz.dto.QuizCreateResponse;
import com.cyes.webserver.domain.quiz.dto.QuizInfoResponse;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.domain.quiz.repository.QuizRepository;
import com.cyes.webserver.domain.quizproblem.repository.QuizProblemRepository;
import com.cyes.webserver.redis.service.ScheduleReserveService;
import com.cyes.webserver.utils.oauth.enums.OAuthProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
class QuizServiceTest {
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private QuizProblemRepository quizProblemRepository;
    //Redis에 quiz를 저장하는 로직은 Mocking 처리함
    @MockBean
    private ScheduleReserveService scheduleReserveService;


    @AfterEach
    void tearDown() {
        quizProblemRepository.deleteAllInBatch();
        quizRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("오늘 현재 시간보다 뒤에 있는 퀴즈 중 가장 먼저 생성된 라이브 퀴즈를 조회한다.")
    @Test
    void searchQuiz(){
        //given
        //Member 생성
        LocalDateTime startTime1 = LocalDateTime.of(2023, 11, 5, 22, 00);
        LocalDateTime startTime2 = LocalDateTime.of(2023, 11, 5, 22, 10);
        LocalDateTime startTime3 = LocalDateTime.of(2023, 11, 5, 22, 20);
        Member member = Member.builder().memberEmail("email@email.com").memberNickname("nickname1").memberAuthority(MemberAuthority.ADMIN).oAuthProvider(OAuthProvider.GUEST).nicknameInitialized(false).build();
        memberRepository.save(member);
        //퀴즈 생성 1,2,3
        Quiz quiz1 = Quiz.builder().member(member).title("퀴즈 제목").startDateTime(startTime1).build();
        Quiz quiz2 = Quiz.builder().member(member).title("퀴즈 제목").startDateTime(startTime2).build();
        Quiz quiz3 = Quiz.builder().member(member).title("퀴즈 제목").startDateTime(startTime3).build();
        quizRepository.saveAll(List.of(quiz1, quiz2, quiz3));
        //현재 시간
        LocalDateTime now1 = LocalDateTime.of(2023, 11, 5, 22, 00);
        LocalDateTime now2 = LocalDateTime.of(2023, 11, 5, 22, 9);
        LocalDateTime now3 = LocalDateTime.of(2023, 11, 5, 22, 19);

        //when
        QuizInfoResponse quizInfoResponse1 = quizService.searchLiveQuiz(now1);
        QuizInfoResponse quizInfoResponse2 = quizService.searchLiveQuiz(now2);
        QuizInfoResponse quizInfoResponse3 = quizService.searchLiveQuiz(now3);

        //then
        assertThat(quizInfoResponse1).extracting("quizId", "quizTitle", "quizStartDate")
                .containsExactly(quiz1.getId(), quiz1.getTitle(), quiz1.getStartDateTime());
        assertThat(quizInfoResponse2).extracting("quizId", "quizTitle", "quizStartDate")
                .containsExactly(quiz2.getId(), quiz2.getTitle(), quiz2.getStartDateTime());
        assertThat(quizInfoResponse3).extracting("quizId", "quizTitle", "quizStartDate")
                .containsExactly(quiz3.getId(), quiz3.getTitle(), quiz3.getStartDateTime());
    }
    @DisplayName("quizTitle, memberId, quizStartDate, problemList로 quiz를 생성할 수 있다.")
    @Test
    void createQuiz() throws Exception {
        //given
        //Member 생성
        Member member = Member.builder().memberEmail("email@email.com").memberNickname("nickname1").memberAuthority(MemberAuthority.ADMIN).oAuthProvider(OAuthProvider.GUEST).nicknameInitialized(false).build();
        memberRepository.save(member);

        //현재시간
        LocalDateTime now = LocalDateTime.now();
        QuizCreateRequestToServiceDto requestToServiceDto = QuizCreateRequestToServiceDto.builder().quizTitle("천하제일 무술대회").memberId(member.getMemberId()).quizStartDate(now).problemList(List.of("문제1", "문제2")).build();

        //when
        QuizCreateResponse createResponse = quizService.createQuiz(requestToServiceDto);

        //then
        //아래 메서드가 호출 됐으면 잘 된것이다.
        verify(scheduleReserveService).saveQuiz(createResponse.getQuizId(),createResponse.getQuizStartDate());

        assertThat(createResponse).extracting("quizTitle","quizStartDate")
                .contains(requestToServiceDto.getQuizTitle(),requestToServiceDto.getQuizStartDate());
    }
}