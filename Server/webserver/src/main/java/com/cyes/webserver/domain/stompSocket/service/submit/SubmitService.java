package com.cyes.webserver.domain.stompSocket.service.submit;

import com.cyes.webserver.domain.stompSocket.dto.SubmitMessage;
import com.cyes.webserver.domain.stompSocket.dto.SubmitRedis;
import com.cyes.webserver.redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubmitService {

    private final RedisService redisService;
    private final StringRedisTemplate stringRedisTemplate;


    /**
     * 클라이언트의 답안 제출을 처리하는 메서드
     * @param message(SubmitMessage)
     * @throws JsonProcessingException
     */
    public void handleSubmit(SubmitMessage message) throws JsonProcessingException {

        // key : 퀴즈번호_문제순서_멤버id
        String key = message.createKey();

        // value : SubmitDto
        SubmitRedis submitRedis = message.ToSubmitRedis(LocalDateTime.parse(redisService.getDataFromRedis(getRedisKey(message))),LocalDateTime.now());
        
        // redis에 답안 제출 정보 저장
        redisService.setSubmitDateRedis(key, submitRedis, Duration.ofMinutes(30));

    }


    /**
     * Redis 검색을 위한 key를 만드는 메서드
     * @param message(Submitmessage)
     * @return quizId_problemOrder (String)
     */
    public String getRedisKey(SubmitMessage message) {
        return message.getQuizId() + "_" + message.getProblemOrder();
    }

    /**
     * 해당 퀴즈로 제출된 답안 List 조회하는 메서드
     * @param quizId (퀴즈 정보)
     * @return List<SubmitRedis> Redis에 퀴즈id로 저장된 제출 정보 List
     */
    public List<SubmitRedis> getSubmitList(String quizId) {

        // 클라이언트가 제출한 정보를 keypattern(submit_퀴즈id*)으로 검색
        String keyPattern = "submit_" + quizId + "*";

        // keypattern으로 검색이 된 key들을 select
        Set<String> values = stringRedisTemplate.keys(keyPattern);

        // Redis에서 가져온 제출 정보를 담을 List 선언
        List<SubmitRedis> submitRedisList = new ArrayList<>();

        // 역직렬화를 위한 objectMapper 선언.
        ObjectMapper objectMapper = new ObjectMapper();
        for (String keyStr : values) {
            try {
                // 제출 정보 key값으로 검색하여 직렬화하여 스트링으로 저장된 제출 정보를 가져온다.
                String valueStr = redisService.getDataFromRedis(keyStr);

                // 가져온 스트링 제출 정보를 SubmitRedis 객체 형태로 역직렬화한다.
                SubmitRedis submitRedis = objectMapper.readValue(valueStr, SubmitRedis.class);

                // 역직렬화한 SubmitRedis 객체를 List에 추가.
                submitRedisList.add(submitRedis);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return submitRedisList;
    }

}
