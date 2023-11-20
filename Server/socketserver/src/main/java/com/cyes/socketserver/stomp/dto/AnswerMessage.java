package com.cyes.socketserver.stomp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AnswerMessage extends SessionMessage{
    String answer;
    int correctNumber;
    int totalNumber;

    @Builder
    public AnswerMessage(Long sessionId, MessageType type, String answer, int correctNumber, int totalNumber) {
        super(sessionId, type);
        this.answer = answer;
        this.correctNumber = correctNumber;
        this.totalNumber = totalNumber;
    }
}
