package com.cyes.webserver.domain.Answer.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RankResult {
    List<String> rankerList = new ArrayList<>();

    @Builder
    public RankResult(List<String> rankerList) {
        this.rankerList = rankerList;
    }
}
