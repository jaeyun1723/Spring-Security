package io.security.basicsecurity;

import io.security.basicsecurity.repository.UserRepository;
import io.security.basicsecurity.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("user");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("admin");
    }

    @GetMapping("/manager")
    public ResponseEntity<String> manager() {
        return ResponseEntity.ok("manager");
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }


    @PostMapping("/join")
    public String join(User user) {
        System.out.println("user = " + user);
        user.setRole("ROLE_USER");
        String rawPassword=user.getPassword();
        String encPassword=bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user); // 회원가입이 잘됨. 비밀번호 : 1234
        // -> 시큐리티로 로그인을 할 수 없음. 이유는 패스워드가 암호화가 안되었기 때문!!
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public ResponseEntity<String> info() {
        return ResponseEntity.ok("개인정보");
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public ResponseEntity<String> data() {
        return ResponseEntity.ok("data");
    }
    @GetMapping("/joinProc")
    public ResponseEntity<String> joinProc() {
        return ResponseEntity.ok("회원가입 완료됨!");
    }
}
