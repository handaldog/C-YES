package com.cyes.webserver.utils.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

import static com.cyes.webserver.redis.KeyGenerator.REFRESH_TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {
    private final String BEARER_TYPE = "Bearer";
    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000;            // 1초
    private final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 6 ;  // 6 시간

    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate stringRedisTemplate;


    public AuthTokens generate(Long memberId) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        // token 생성
        String subject = memberId.toString();
        String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredAt);
        String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredAt);

        // Redis에 refresh token 저장
        stringRedisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX+memberId, refreshToken);
        stringRedisTemplate.expire(REFRESH_TOKEN_PREFIX+memberId, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_TIME));

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    public boolean verifyToken(String accessToken) {
        return jwtTokenProvider.verifyToken(accessToken);
    }

    public Long extractMemberId(String accessToken) {
        return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
    }
}