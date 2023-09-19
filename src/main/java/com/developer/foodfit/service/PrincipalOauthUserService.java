package com.developer.foodfit.service;

import com.developer.foodfit.config.oauth.GoogleUserInfo;
import com.developer.foodfit.config.oauth.KakaoUserInfo;
import com.developer.foodfit.config.oauth.NaverUserInfo;
import com.developer.foodfit.config.oauth.OAuth2UserInfo;
import com.developer.foodfit.constant.Role;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.PrincipalDetails;
import com.developer.foodfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalOauthUserService extends DefaultOAuth2UserService {

    final private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{


        /** OAuth 로그인 / 회원가입 **/
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo((Map) oAuth2User.getAttributes().get("kakao-account"),
            String.valueOf(oAuth2User.getAttributes().get("id")));
        }else{
            log.info("지원하지 않는 로그인 서비스 입니다.");
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = oAuth2UserInfo.getName();
        String userId = provider+"_"+providerId;
        String email = oAuth2UserInfo.getEmail();
        Role role = Role.GUEST;

        User emailEntity = userRepository.findByEmail(email);
        User userEntity = userRepository.findByProviderId(providerId);

        if(userEntity==null){
            if(emailEntity==null){
                LocalDateTime createTime = LocalDateTime.now();

                userEntity = User.builder()
                        .username(username)
                        .email(email)
                        .userId(userId)
                        .role(role)
                        .provider(provider)
                        .providerId(providerId)
                        .createDate(createTime)
                        .build();

                userRepository.save(userEntity);
            }else{
                throw new OAuth2AuthenticationException("이미 가입된 사용자입니다");
            }
        }
        return new PrincipalDetails(userEntity,oAuth2User.getAttributes());
    }
}
