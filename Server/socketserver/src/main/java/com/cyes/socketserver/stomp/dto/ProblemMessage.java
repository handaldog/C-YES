package com.cyes.socketserver.stomp.dto;

import com.cyes.socketserver.stomp.ProblemType;
import lombok.*;

import java.util.List;

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
