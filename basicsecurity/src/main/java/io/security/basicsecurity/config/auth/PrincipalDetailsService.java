package io.security.basicsecurity.config.auth;

import io.security.basicsecurity.repository.UserRepository;
import io.security.basicsecurity.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {
    // 시큐리티 설정에서 loginProcessingUrl("/login);
    // login 요청이 오면 자동으로 UserDetailsService타입으로 IoC되어있는 loadUserByUsername 함수가 실행
    @Autowired
    private UserRepository userRepository;

    // 시큐리티 session(내부 Authentication(내부 UserDetails))
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null)
            return new PrincipalDetails(userEntity);
        return null;
    }
}
