package io.security.basicsecurity.config.auth.oauth;

import io.security.basicsecurity.config.auth.PrincipalDetails;
import io.security.basicsecurity.repository.UserRepository;
import io.security.basicsecurity.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;


    // 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
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

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("userRequest.getAccessToken() = " + userRequest.getAccessToken().getTokenValue());
        // 구글로그인 버튼 클릭 -> 구글 로그인창 -> 로그인을 완료 -> code를 리턴(OAuth-Client라이브러리) -> AccessToken요청
        // userRequest 정보 -> 회원프로필 받아야함(loadUser함수) -> 구글로부터 회원프로필 받아준다.
        System.out.println("userRequest.getClientRegistration() = " + super.loadUser(userRequest).getAttributes());

        // 회원가입을 강제로 진행해볼 예정
        String provider = userRequest.getClientRegistration().getClientId(); // google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String email = oAuth2User.getAttribute("email");
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            System.out.println("구글 로그인 최초입니다.");
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }else{
            System.out.println("구글 로그인을 이미 한 적이 있습니다.");
        }

        // authentication객체에 들어감
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
