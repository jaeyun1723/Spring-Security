package com.ssafy.fiveguys.game.user.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userSequence;
    private String userId;
    private String password;
    private String email;
    private String name;
    private String nickname;
    private String mainAchievement;
    private Role role;
    private String profileNumber;
    private Long level;
    private Long point;
    private SocialType socialType;
    private String socialId;
    private Timestamp creationDate;
    private Timestamp lastModifiedDate;
}
