package com.ssafy.fiveguys.game.user.dto;

import com.ssafy.fiveguys.game.user.config.JwtProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class JwtTokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
