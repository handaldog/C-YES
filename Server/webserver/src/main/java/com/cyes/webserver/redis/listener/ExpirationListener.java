package com.cyes.webserver.redis.listener;

import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import static com.cyes.webserver.redis.KeyGenerator.SCHEDULE_USER_PREFIX;

/**
 * Redis의 Key가 만료될 때, 발생하는 이벤트를 핸들링하는 컴포넌트
 */

@Slf4j
@Component
public class ExpirationListener extends KeyExpirationEventMessageListener {

    private final ScheduledTaskFactory scheduledTaskFactory;
    private final String SCHEDULE_PREFIX = "ScheduledQuizSession_";

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public ExpirationListener(RedisMessageListenerContainer listenerContainer,
                              ScheduledTaskFactory scheduledTaskFactory
    ) {
        super(listenerContainer);
        this.scheduledTaskFactory = scheduledTaskFactory;
    }

    /**
     * Redis의 ExpiredEvent 발생시 실행하는 함수
     * expired된 객체의 key를 메세지로 받아 quizId를 추출하고, 해당 quizId에 대한
     * QuizTask를 실행한다.
     *
     * @param message message must not be {@literal null}.
     * @param pattern pattern matching the channel (if specified) - can be {@literal null}.
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();

        System.out.println(key);

        String quizId;

        if (key.contains(SCHEDULE_PREFIX))
            quizId = key.replace(SCHEDULE_PREFIX, "");
        else if(key.contains(SCHEDULE_USER_PREFIX))
            quizId = key.replace(SCHEDULE_USER_PREFIX, "");
        else
            return;


        try {
            scheduledTaskFactory.quizProcedureTask(Long.parseLong(quizId));
        } catch (InterruptedException e) {
            throw new CustomException(CustomExceptionList.SCHEDULE_CREATE_FAIL_ERROR);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}