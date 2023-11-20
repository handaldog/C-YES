package com.cyes.webserver.domain.stompSocket.dto;

import com.cyes.webserver.domain.problem.entity.ProblemType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProblemMessage extends SessionMessage{
    String question;
    Integer order;
    String answerLength;
    String[] selections;
    ProblemType problemType;

    public enum QuestionType {
        SINGLE, FOUR, OX
    }


    @Builder
    public ProblemMessage(Long quizId, MessageType type, String question, Integer order, String answerLength, String[] selections, ProblemType problemType) {
        super(quizId, type);
        this.question = question;
        this.order = order;
        this.answerLength = answerLength;
        this.selections = selections;
        this.problemType = problemType;
    }

}
