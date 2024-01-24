package com.ssafy.fiveguys.game.user.service;

import com.ssafy.fiveguys.game.user.auth.JwtTokenProvider;
import com.ssafy.fiveguys.game.user.config.SecurityConfig;
import com.ssafy.fiveguys.game.user.dto.JwtTokenDto;
import com.ssafy.fiveguys.game.user.dto.LoginDto;
import com.ssafy.fiveguys.game.user.dto.UserDto;
import com.ssafy.fiveguys.game.user.entity.User;
import com.ssafy.fiveguys.game.user.exception.PasswordException;
import com.ssafy.fiveguys.game.user.exception.UserIdNotFoundException;
import com.ssafy.fiveguys.game.user.repository.UserRepositoy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public JwtTokenDto login(LoginDto loginDto) {
        System.out.println("AuthService.login - start");
        UserDto user = userService.findUserById(loginDto.getUserId());
        if (user == null) {
            throw new UserIdNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new PasswordException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
            = new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());

        Authentication authentication =
            authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("AuthService.login - finish");
        // refresh-token redis에 저장해야함
        return jwtTokenProvider.generateToken(authentication);
        
    }
}
