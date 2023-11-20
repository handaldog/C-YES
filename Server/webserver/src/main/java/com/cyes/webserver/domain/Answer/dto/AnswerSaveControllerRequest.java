package com.cyes.webserver.domain.Answer.dto;

import com.cyes.webserver.domain.Answer.entity.Answer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerSaveControllerRequest {
    @NotNull(message = "memberId는 필수입니다.")
    private Long memberId;

    @NotNull(message = "quizId는 필수입니다.")
    private Long quizId;

    @NotNull
    @Min(value = 1,message = "문제번호는 필수 입니다. 1이상의 정수입니다.")
    private Integer problemNumber;

    @NotNull(message = "submitContent는 필수입니다.")
    private String submitContent;


    @Builder
    public AnswerSaveControllerRequest(Long memberId, Long quizId, Integer problemNumber, String submitContent) {
        this.memberId = memberId;
        this.quizId = quizId;
        this.problemNumber = problemNumber;
        this.submitContent = submitContent;
    }

    public AnswerSaveServiceRequest toServiceRequest(){
        return AnswerSaveServiceRequest.builder()
                .memberId(this.memberId)
                .quizId(this.quizId)
                .problemNumber(this.problemNumber)
                .submitContent(this.submitContent).build();
    }
}
