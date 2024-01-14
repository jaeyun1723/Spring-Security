package io.security.basicsecurity.config.auth.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    // 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // username = google_114872967575817570421 -> oauth인증시 구글에서 주는 attributes 중 pk
        // password = 아무거나
        // email = google에서 받은 이메일
        // role = ROLE_USER
        // provider = "google"
        // providerId = 14872967575817570421

        //registerationId로 어떤 Oauth로 로그인 했는지 알 수 있음.
        System.out.println("userRequest = " + userRequest.getClientRegistration());
        
        System.out.println("userRequest.getAccessToken() = " + userRequest.getAccessToken().getTokenValue());
        // 구글로그인 버튼 클릭 -> 구글 로그인창 -> 로그인을 완료 -> code를 리턴(OAuth-Client라이브러리) -> AccessToken요청
        // userRequest 정보 -> 회원프로필 받아야함(loadUser함수) -> 구글로부터 회원프로필 받아준다.
        System.out.println("userRequest.getClientRegistration() = " + super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User=super.loadUser(userRequest);
        return super.loadUser(userRequest);
    }
}
