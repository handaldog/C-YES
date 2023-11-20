package com.cyes.socketserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override // 메시지를 중간에서 라우팅할 때 사용하는메시지 브로커를 구성
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 해당 주소를 구독하는 클라이언트에게 메세지를 보낸다. 예를 들어 1번 채널을 구독하려면 /sub/1 형식으로 보내야 한다
        registry.enableSimpleBroker("/sub");

        // 메세지 발행 요청의 prefix를 지정한다. 따라서 /pub로 시작하는 메시지만 해당 Broker에서 받아서 처리후 /sub에 전달한다
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override // 클라이언트에서 접속할 endpoint 설정
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/quiz")
//                .setAllowedOriginPatterns("http://localhost:9510", "https://k9b103.p.ssafy.io", "https://cyes.site")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
