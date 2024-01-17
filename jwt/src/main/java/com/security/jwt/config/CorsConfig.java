package com.security.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
        // setAllowCredentials : 쿠키 요청을 허용하도록 true로 설정
        config.addAllowedOrigin("*"); // 모든 ip에 응답을 허용하겠다.
        //addAllowedOrigin : 허용할 출처를 입력(프론트엔드의 도메인과 포트를 입력하면 된다.)
        config.addAllowedHeader("*"); // 모든 header에 응답을 허용하겠다.
        config.addAllowedMethod("*"); // 모든 post, get, put, delete, patch 요청을 허용하겠다.
        source.registerCorsConfiguration("/api/**", config);
        // source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
