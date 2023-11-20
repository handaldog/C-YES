package com.cyes.webserver.redis.service;

import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.cyes.webserver.redis.KeyGenerator.SCHEDULE_PREFIX;
import static com.cyes.webserver.redis.KeyGenerator.SCHEDULE_USER_PREFIX;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleReserveService {

    private final StringRedisTemplate stringRedisTemplate;


    /**
     *
     * expire scheduler redis에 prefix + quizID를 한 키값을 저장해 작업을 예약한다.
     *
     * @param quizId 스케줄링 할 퀴즈의 ID
     * @param expireTime 퀴즈 시작 시간 (키 만료 시간)
     */
    public void saveQuiz(Long quizId, LocalDateTime expireTime) {

        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();

        // 입력 된 시간이 현재 시간보다 이전이면 예외처리
        if (expireTime.isBefore(LocalDateTime.now())) {
            throw new CustomException(CustomExceptionList.SCHEDULE_START_TIME_TOO_EARLY_ERROR);
        }

        String quizIdStr = SCHEDULE_PREFIX + quizId;
        op.set(quizIdStr, "");

        Duration expireDuration = Duration.between(LocalDateTime.now(), expireTime);

        stringRedisTemplate.expire(quizIdStr, expireDuration);
    }

    /**
     *
     * expire scheduler redis에 유저가 생성한 퀴즈에 대하여 prefix + quizID를 한 키값을 저장해 작업을 예약한다.
     *
     * @param quizId 스케줄링 할 퀴즈의 ID
     * @param expireTime 퀴즈 시작 시간 (키 만료 시간)
     */
    public void saveUserQuiz(Long quizId, LocalDateTime expireTime) {

        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();

        // 입력 된 시간이 현재 시간보다 이전이면 예외처리
        if (expireTime.isBefore(LocalDateTime.now())) {
            throw new CustomException(CustomExceptionList.SCHEDULE_START_TIME_TOO_EARLY_ERROR);
        }

        String quizIdStr = SCHEDULE_USER_PREFIX + quizId;
        op.set(quizIdStr, "");

        Duration expireDuration = Duration.between(LocalDateTime.now(), expireTime);

        stringRedisTemplate.expire(quizIdStr, expireDuration);
    }

    /**
     *
     * 아직 시작하지 않은 퀴즈의 시작 시간을 변경한다.
     * 
     * @param quizId 스케줄링 된 퀴즈의 ID
     * @param expireTime 교체할 퀴즈 시작 시간
     */
    public void updateQuiz(Long quizId, LocalDateTime expireTime) {

        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();

        String quizIdStr = SCHEDULE_PREFIX + quizId;

        // 입력 된 시간이 현재 시간보다 이전이면 예외처리
        if (expireTime.isBefore(LocalDateTime.now())) {
            throw new CustomException(CustomExceptionList.SCHEDULE_START_TIME_TOO_EARLY_ERROR);
        }

        if (op.get(quizIdStr) == null) {
            throw new CustomException(CustomExceptionList.SCHEDULE_NOT_FOUND_ERROR);
        }

        Duration expireDuration = Duration.between(LocalDateTime.now(), expireTime);

        stringRedisTemplate.expire(quizIdStr, expireDuration);
    }

    /**
     * 
     * 스케줄링 된 퀴즈를 취소한다
     * 
     * @param quizId 스케줄링 된 퀴즈 ID
     */
    public void deleteQuiz(Long quizId){
        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();

        String quizIdStr = SCHEDULE_PREFIX + quizId;

        if (op.getAndDelete(quizIdStr) == null) {
            throw new CustomException(CustomExceptionList.SCHEDULE_NOT_FOUND_ERROR);
        }
    }
}
