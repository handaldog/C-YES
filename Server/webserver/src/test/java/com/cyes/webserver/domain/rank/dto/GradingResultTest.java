package com.cyes.webserver.domain.rank.dto;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GradingResultTest {

    @Test
    @DisplayName("정렬하면 맞은 문제, 걸린 시간으로 정렬된다.")
    void sortTest() {
        // given
        List<GradingResult> resultList = getGradingResultList();

        // when
        Collections.sort(resultList);

        // then
        assertThat(resultList).hasSize(7)
                .extracting("correctProblemCount", "correctProblemDuringTime")
                .containsExactly(Tuple.tuple(10, 20000000L)
                        , Tuple.tuple(5, 2L)
                        , Tuple.tuple(3, 200L)
                        , Tuple.tuple(3, 201L)
                        , Tuple.tuple(3, 202L)
                        , Tuple.tuple(2, 200L)
                        , Tuple.tuple(2, 2000L)
                );
    }

    @Test
    @DisplayName("맞은 문제와 시간을 추가하면 정렬순서가 바뀐다.")
    void addProblemTime() {
        // given
        List<GradingResult> gradingResultList = getGradingResultList();
        Collections.sort(gradingResultList);

        // when
        gradingResultList.get(1).addCorrectCount();
        gradingResultList.get(4).addCorrectCount();
        gradingResultList.get(4).addDuringTime(200L);
        Collections.sort(gradingResultList);

        // then
        assertThat(gradingResultList).hasSize(7)
                .extracting("correctProblemCount", "correctProblemDuringTime")
                .containsExactly(Tuple.tuple(10, 20000000L)
                        , Tuple.tuple(6, 2L)
                        , Tuple.tuple(4, 402L)
                        , Tuple.tuple(3, 200L)
                        , Tuple.tuple(3, 201L)
                        , Tuple.tuple(2, 200L)
                        , Tuple.tuple(2, 2000L)
                );
    }

    private List<GradingResult> getGradingResultList(){
        List<GradingResult> list = new ArrayList<>();

        list.add(GradingResult.builder().correctProblemCount(2).correctProblemDuringTime(2000L).build());
        list.add(GradingResult.builder().correctProblemCount(3).correctProblemDuringTime(200L).build());
        list.add(GradingResult.builder().correctProblemCount(2).correctProblemDuringTime(200L).build());
        list.add(GradingResult.builder().correctProblemCount(10).correctProblemDuringTime(20000000L).build());
        list.add(GradingResult.builder().correctProblemCount(3).correctProblemDuringTime(201L).build());
        list.add(GradingResult.builder().correctProblemCount(5).correctProblemDuringTime(2L).build());
        list.add(GradingResult.builder().correctProblemCount(3).correctProblemDuringTime(202L).build());

        return list;
    }
}