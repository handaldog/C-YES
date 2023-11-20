package com.cyes.socketserver.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig  implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:9510", "https://k9b103.p.ssafy.io", "https://cyes.site")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
    }
}
