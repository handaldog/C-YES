package com.cyes.webserver.interceptor;

import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import com.cyes.webserver.utils.jwt.AuthTokensGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthTokensGenerator authTokensGenerator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        String token = request.getHeader("token");
        if (token != null && authTokensGenerator.verifyToken(token)) {
            log.info("access confirmed");
            return true;
        }
        log.error("access denied");
        log.error(request.getRequestURI());
        throw new CustomException(CustomExceptionList.ACCESS_TOKEN_ERROR);
    }
}