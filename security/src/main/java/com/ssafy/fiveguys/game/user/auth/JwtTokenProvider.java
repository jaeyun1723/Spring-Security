package com.ssafy.fiveguys.game.user.auth;

import com.ssafy.fiveguys.game.user.config.JwtProperties;
import com.ssafy.fiveguys.game.user.dto.JwtTokenDto;
import com.ssafy.fiveguys.game.user.service.GameUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "role";

    private static final String USER_ID = "userId";
    private final GameUserDetailsService gameUserDetailsService;
    private final Key key;

    public JwtTokenProvider(GameUserDetailsService gameUserDetailsService) {
        System.out.println("JwtTokenProvider.JwtTokenProvider 생성");
        this.gameUserDetailsService = gameUserDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(JwtProperties.SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // accessToken, refreshToken을 생성
    public JwtTokenDto generateToken(Authentication authentication) {
        System.out.println("JwtTokenProvider.generateToken -  start");
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date accessTokenExpirationTime = new Date(now + JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME);
        GameUserDetails principal = (GameUserDetails) authentication.getPrincipal();

        String accessToken = Jwts.builder()
            .setSubject(JwtProperties.ACCESS_TOKEN)
            .setExpiration(accessTokenExpirationTime)
            .claim(USER_ID, principal.getUsername())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        String refreshToken = Jwts.builder()
            .setSubject(JwtProperties.REFRESH_TOKEN)
            .setExpiration(new Date(now + JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        System.out.println("JwtTokenProvider.generateToken - finish");
        return JwtTokenDto.builder()
            .grantType(JwtProperties.TOKEN_PREFIX)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    // 토큰으로부터 정보 추출
    private Claims parseClaims(String accessToken) {
        System.out.println("JwtTokenProvider.parseClaims");
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰으로부터 추출한 정보를 기반으로 AuthenticationToken 객체 생성
    public Authentication getAuthentication(String accessToken) {
        System.out.println("JwtTokenProvider.getAuthentication - start");
        Claims claims = parseClaims(accessToken);
        String userId = claims.get(USER_ID).toString();
        UserDetails principal = gameUserDetailsService.loadUserByUsername(userId);
        System.out.println("JwtTokenProvider.getAuthentication - finish");
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        System.out.println("JwtTokenProvider.validateToken - start");
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            System.out.println("JwtTokenProvider.validateToken - finish");
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

}
