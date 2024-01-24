package com.ssafy.fiveguys.game.user.filter;

import com.ssafy.fiveguys.game.user.auth.JwtTokenProvider;
import com.ssafy.fiveguys.game.user.config.JwtProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    private String resolveToken(HttpServletRequest request) {
        System.out.println("JwtAuthenticationFilter.resolveToken - start");
        String token = request.getHeader(JwtProperties.HEADER);
        if (StringUtils.hasText(token) && token.startsWith(JwtProperties.TOKEN_PREFIX)) {
            System.out.println("JwtAuthenticationFilter.resolveToken - finish");
            return token.substring(7);
        }
        return null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        System.out.println("JwtAuthenticationFilter.doFilter - start");
        // Request Header에서 JWT Token 추출
        String token = resolveToken((HttpServletRequest) request);
        // validateToken으로 토큰 유효성 검사
        if (token != null || jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        System.out.println("JwtAuthenticationFilter.doFilter - finish");
        chain.doFilter(request, response);
    }
}
