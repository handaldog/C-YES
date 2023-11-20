package com.cyes.socketserver.stomp.service.enter;

import com.cyes.socketserver.stomp.dto.SessionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.cyes.socketserver.KeyGenerator.TOTAL_PARTICIPANT;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnterService {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 퀴즈 입장을 처리하는 메서드
     */
    public void handleEnter(SessionMessage message) {
        String key = TOTAL_PARTICIPANT+message.getQuizId();

        stringRedisTemplate.opsForValue().increment(key);
    }

    /**
     * 퀴즈 퇴장을 처리하는 메서드
     */
    public void handleDisconnect(SessionMessage message) {
        String key = TOTAL_PARTICIPANT+message.getQuizId();

        stringRedisTemplate.opsForValue().decrement(key);
    }
}
