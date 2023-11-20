package com.cyes.webserver.domain.adminQuiz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Usage {
    public String prompt_tokens;
    public String completion_tokens;
    public String total_tokens;
}