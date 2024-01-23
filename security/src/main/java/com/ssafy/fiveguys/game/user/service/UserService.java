package com.ssafy.fiveguys.game.user.service;

import com.ssafy.fiveguys.game.user.dto.Role;
import com.ssafy.fiveguys.game.user.dto.UserSignDto;
import com.ssafy.fiveguys.game.user.entity.User;
import com.ssafy.fiveguys.game.user.repository.UserRepositoy;
import jakarta.transaction.Transactional;
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

    public void signUp(UserSignDto userSignDto) throws Exception {
        User user = User.builder()
            .userId(userSignDto.getUserId())
            .username(userSignDto.getUsername())
            .password(passwordEncoder.encode(userSignDto.getPassword()))
            .nickname(userSignDto.getNickname())
            .role(Role.ROLE_USER)
            .build();

        userRepositoy.save(user);
    }
}
