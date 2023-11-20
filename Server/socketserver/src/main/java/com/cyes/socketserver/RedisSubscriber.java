package com.cyes.socketserver;

import com.cyes.socketserver.stomp.dto.AnswerMessage;
import com.cyes.socketserver.stomp.dto.ProblemMessage;
import com.cyes.socketserver.stomp.dto.ResultMessage;
import com.cyes.socketserver.stomp.dto.SessionMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        log.info("받았다 기모띠");
//    }

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    //Redis로부터 메시지가 도착했을 때 실행되는 메서드
    @Override
    //publish된 메서드가 message를 통해서 들어온다.
    public void onMessage(Message message, byte[] pattern) {

            //message.getBody()를 하면 byte형식 배열이 반환된다.
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());


        SessionMessage roomMessage = null;
        try {
            roomMessage = objectMapper.readValue(publishMessage, SessionMessage.class);
        if (roomMessage.getType().equals(SessionMessage.MessageType.PROBLEM)) {
                    roomMessage = objectMapper.readValue(publishMessage, ProblemMessage.class);
                } else if (roomMessage.getType().equals(SessionMessage.MessageType.ANSWER)) {
                    roomMessage = objectMapper.readValue(publishMessage, AnswerMessage.class);
                } else if (roomMessage.getType().equals(SessionMessage.MessageType.RESULT)) {
                    roomMessage = objectMapper.readValue(publishMessage, ResultMessage.class);
                }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info(roomMessage.toString());

        //stomp Message Broker로 보내는 메서드
        //즉 client에게 보내는 메서드는 이곳이다. MessageService에서는 모두 Redis에 publish하는 것
        messagingTemplate.convertAndSend("/sub/quiz/session/" + roomMessage.getQuizId(), roomMessage);
    }
}


