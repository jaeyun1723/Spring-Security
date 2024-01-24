package com.ssafy.fiveguys.game.user.controller;

import com.ssafy.fiveguys.game.user.config.JwtProperties;
import com.ssafy.fiveguys.game.user.dto.JwtTokenDto;
import com.ssafy.fiveguys.game.user.dto.LoginDto;
import com.ssafy.fiveguys.game.user.service.AuthService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        JwtTokenDto jwtTokenDto = authService.login(loginDto);
        Map<String, Object> response = new HashMap<>();
        response.put(JwtProperties.GRANT_TYPE, JwtProperties.TOKEN_PREFIX);
        response.put(JwtProperties.ACCESS_TOKEN, jwtTokenDto.getAccessToken());
        response.put(JwtProperties.REFRESH_TOKEN, jwtTokenDto.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
