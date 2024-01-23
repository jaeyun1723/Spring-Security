package com.ssafy.fiveguys.game.user.auth;

import io.jsonwebtoken.io.Decoders;
import java.util.Base64.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY="auth";

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessToken.expiration}")
    private Long accessTokenExpirationTime;

    @Value("${jwt.refreshToken.expiration}")
    private Long refreshTokenExpirationTime;

    @Value("${jwt.accessToken.header}")
    private String accessTokenHeader;

    @Value("${jwt.refreshToken.header}")
    private String refreshTokenHeader;

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey) {
        byte[] keyBytes= Decoders.
    }



}
