package io.security.basicsecurity.config;

import io.security.basicsecurity.config.auth.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize,postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;


    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
//    @Bean
//    public BCryptPasswordEncoder encodePwd() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 로그인 인증 필요
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                // admin 이나 manager 권한이 있어야 들어갈 수 있음
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                // admin 권한이 있어야 들어갈 수 있음.
                .anyRequest().permitAll() // 나머지 모든 요청 허용

                .and()

                .formLogin()
                .loginPage("/loginForm") // 권한이 필요한 경우 다 로그인 페이지로 이동함.
                .loginProcessingUrl("/login") // login주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행.
                .defaultSuccessUrl("/") // 특정 페이지에서 로그인 해야되면 하고 바로 가려고자하는 페이지 보여줌

                .and()

                .oauth2Login()
                .loginPage("/loginForm") // 구글 로그인이 완료된 뒤의 후처리가 필요함.
                // 1. 코드받기(인증)
                // 2. 엑세스토큰(권한)
                // 3.사용자프로필 정보를 가져와서
                // 4-1.그 정보를 토대로 회원가입을 자동으로 진행
                // Tip. 코드 X, (엑세스 토큰+ 사용자 프로필 정보 O)
                .userInfoEndpoint()
                .userService(principalOauth2UserService) // 후처리
                ;
    }
}
