package com.cyes.socketserver.stomp.service.chat;

import com.cyes.socketserver.stomp.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final SimpMessageSendingOperations messagingTemplate;


    /**
     * 클라이언트가 보낸 채팅 정보를 처리하는 메서드
     * @param message(ChatMessage)
     */
    public void handleChat(ChatMessage message) {
        //stomp Message Broker로 보내는 메서드
        //즉 client에게 보내는 메서드는 이곳이다. MessageService에서는 모두 Redis에 publish하는 것
        messagingTemplate.convertAndSend("/sub/quiz/session/" + message.getQuizId(), message);
    }

}
