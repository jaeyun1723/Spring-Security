package com.ssafy.fiveguys.game.common.controller;

import com.ssafy.fiveguys.game.common.aop.TimeTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    // 회원가입
    // 로그인
    // refreshtoken
    @TimeTrace
    @GetMapping("/d")
    public String index(){
        System.out.println("gdgd");
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(1);
        arr.add(1);
        arr.add(1);
        arr.add(1);
        arr.add(1);
        arr.add(1);
        arr.add(1);
        arr.add(1);
        return "fininsh";
    }

}
