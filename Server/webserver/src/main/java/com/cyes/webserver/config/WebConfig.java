package com.cyes.webserver.config;

import com.cyes.webserver.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:9510", "https://k9b103.p.ssafy.io", "https://cyes.site")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/3.52.5/");
    }


//    @Override
//    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
//
//        interceptorRegistry.addInterceptor(authInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/api/oauth/login/**",
//                        "/api/oauth/regen",
//                        "/api/sessions/**",
//                        "/gameroom/**",
//                        "/chatroom/**",
//                        "/gwh-websocket/**",
//                        "/v3/api-docs/**",  // Swagger JSON 경로
//                        "/swagger-resources/**",  // Swagger 리소스 경로
//                        "/swagger-ui/**",  // Swagger UI 경로,
//                        "/api/problem/**"
//
//                );
//    }
}