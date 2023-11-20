package com.cyes.webserver.domain.adminQuiz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class outNoCheckShortProblemDTO{

    private String id;
    private String question;
    private String category;


    @Builder
    private outNoCheckShortProblemDTO(String id,String question, String category){
        this.id = id;
        this.question = question;
        this.category = category;
    }

}
