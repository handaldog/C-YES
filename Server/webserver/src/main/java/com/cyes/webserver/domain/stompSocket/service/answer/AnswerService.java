package com.cyes.webserver.domain.stompSocket.service.answer;

import com.cyes.webserver.domain.problem.dto.response.ProblemResponse;
import com.cyes.webserver.domain.stompSocket.dto.AnswerMessage;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import com.cyes.webserver.domain.stompSocket.dto.SubmitRedis;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("AnswerService")
@Slf4j
@RequiredArgsConstructor
public class AnswerService {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final ChannelTopic channelTopic;

    /**
     * client한테 문제의 정답을 보여주는 메서드
     * @param quizId (퀴즈id)
     * @param problem (퀴즈정보)
     */
    public void sendAnswer(Long quizId, ProblemResponse problem, int totalParticipants) throws JsonProcessingException {

        String answer = problem.getAnswer();

        int correctNumber = calcCorrectAnswer(quizId, problem);

        AnswerMessage answerMessage = AnswerMessage.builder()
                .sessionId(quizId)
                .type(SessionMessage.MessageType.ANSWER)
                .answer(problem.getAnswer())
                .correctNumber(correctNumber)
                .totalNumber(totalParticipants)
                .build();

        // 클라이언트한테 답 보내기
        redisTemplate.convertAndSend(channelTopic.getTopic(), answerMessage);
    }

    public int calcCorrectAnswer(Long quizId, ProblemResponse problem) throws JsonProcessingException {
        String keyPattern = "submit_" + quizId + "_" + problem.getProblemOrder() + "*";

        int correctNumber = 0;

        Set<String> keys = stringRedisTemplate.keys(keyPattern);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        for(String key : keys) {
            String value = ops.get(key);
            SubmitRedis submitRedis = objectMapper.readValue(value, SubmitRedis.class);

            if (problem.getAnswer().equals(submitRedis.getSubmitContent()))
                correctNumber++;
        }

        return correctNumber;
    }
}
