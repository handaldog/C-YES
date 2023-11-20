package com.cyes.webserver.domain.stompSocket.service.end;


import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EndService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

    /**
     * client에게 퀴즈가 종료되었음을 알리는 메서드
     * @param quizId (퀴즈id)
     */
    public void sendEnd(Long quizId) {

        SessionMessage endMessage = new SessionMessage(quizId, SessionMessage.MessageType.END);

        redisTemplate.convertAndSend(channelTopic.getTopic(), endMessage);
    }
}
