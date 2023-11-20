package com.cyes.webserver.domain.Answer.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class AnswerTest {
    @Test
    @DisplayName("LocalDateTime 비교하는 메서드 의 작동을 확인한다.")
    void compareTo() {
        // given
        LocalDateTime time1 = LocalDateTime.of(2023, 10, 30, 16, 00, 00,0);
        LocalDateTime time2 = LocalDateTime.of(2023, 10, 30, 16, 30, 00,0);

        // when
        Duration between = Duration.between(time1, time2);
        long nanos = between.toNanos();

        System.out.println("nano = " + nanos);

        // then
                                                // 분, 초
        assertThat(nanos).isEqualTo(30*60000000000L);
    }
    @Test
    @DisplayName("Answer Entity 를 생성한다.")
    void answerCreateTest() {
        // given
        String id = "pk가 들어갈 값입니다.";
        Long memberId = 1L;
        Long quizId = 2L;
        Integer problemNumber = 3;
        String submitContent = "제출 답안";

        // when
        Answer answer = Answer.builder()
                .id(id)
                .memberId(memberId)
                .quizId(quizId)
                .problemNumber(problemNumber)
                .submitContent(submitContent)
                .build();
        // then
        assertThat(answer).extracting("id", "memberId", "quizId", "problemNumber", "submitContent")
                .containsExactly(id, memberId, quizId, problemNumber, submitContent);
    }

}