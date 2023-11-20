package com.cyes.webserver.domain.stompSocket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SessionMessage {
    //세션 ID
    Long quizId;
    //내용
    MessageType type;


    /**
     * ENTER : 세션 접속 메세지
     * TALK : 채팅 메시지
     * EXIT : 세션 나가기 메세지
     * RPC : 서버 명령 메세지
     */
    public enum MessageType {
        ENTER, START, PROBLEM, SUBMIT, ANSWER, END, RESULT, CHAT
    }

}