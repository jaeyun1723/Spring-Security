package com.ssafy.fiveguys.game.user.service;

import com.ssafy.fiveguys.game.user.dto.Role;
import com.ssafy.fiveguys.game.user.dto.UserDto;
import com.ssafy.fiveguys.game.user.dto.UserSignDto;
import com.ssafy.fiveguys.game.user.entity.User;
import com.ssafy.fiveguys.game.user.repository.UserRepositoy;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepositoy userRepositoy;
    private final BCryptPasswordEncoder passwordEncoder;
    private final String PROFILE_DEFAULT = "000";
    private final Long FIRST_LEVEL = 1L;
    private final Long INITIAL_POINT = 0L;

    public void signUp(UserSignDto userSignDto) {
        User user = User.builder()
            .userId(userSignDto.getUserId())
            .name(userSignDto.getName())
            .password(passwordEncoder.encode(userSignDto.getPassword()))
            .email(userSignDto.getEmail())
            .nickname(userSignDto.getNickname())
            .role(Role.USER)
            .profileNumber(PROFILE_DEFAULT)
            .level(FIRST_LEVEL)
            .point(INITIAL_POINT)
            .build();

        userRepositoy.save(user);
    }

    public UserDto findUserById(String userId) {
        User user = userRepositoy.findByUserId(userId).orElseThrow(
            () -> new NoSuchElementException("존재하지 않는 유저 입니다."));
        return UserDto.builder()
            .userSequence(user.getUserSequence())
            .userId(user.getUserId())
            .password(user.getPassword())
            .email(user.getEmail())
            .name(user.getName())
            .nickname(user.getNickname())
            .mainAchievement(user.getMainAchievement())
            .role(user.getRole())
            .profileNumber(user.getProfileNumber())
            .level(user.getLevel())
            .point(user.getPoint())
            .socialType(user.getSocialType())
            .socialId(user.getSocialId())
            .creationDate(user.getCreationDate())
            .lastModifiedDate(user.getLastModifiedDate())
            .build();
    }
}
