package com.cyes.socketserver.stomp.controller;

import com.cyes.socketserver.stomp.dto.ChatMessage;
import com.cyes.socketserver.stomp.dto.SessionMessage;
import com.cyes.socketserver.stomp.dto.SubmitMessage;
import com.cyes.socketserver.stomp.service.chat.ChatService;
import com.cyes.socketserver.stomp.service.enter.EnterService;
import com.cyes.socketserver.stomp.service.submit.SubmitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SubmitService submitService;
    private final ChatService chatService;
    private final EnterService enterService;

    /**
     * 유저 입장 핸들링
     */
    @MessageMapping("/session/message/enter")
    public void enter(SessionMessage message) {
        enterService.handleEnter(message);
    }

    /**
     * 유저 퇴장 핸들링
     */
    @MessageMapping("/session/message/disconnect")
    public void disconnect(SessionMessage message) {
        enterService.handleDisconnect(message);
    }

    /**
     * 유저 답안 제출 핸들링
     */
    @MessageMapping("/session/message/submit")
    public void submit(SubmitMessage message) throws JsonProcessingException {
        log.info("message = {}", message);
        submitService.handleSubmit(message);
    }
    
    /**
     * 유저 채팅 핸들링
     */
    @MessageMapping("/session/message/chat")
    public void chat(ChatMessage message) {
        chatService.handleChat(message);
    }

}