package com.developer.foodfit.config.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@Component
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    //소셜 로그인 실패시 동작
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.info("소셜 로그인에 실패했습니다. 에러 메시지 : {}", exception.getMessage());
        String errorMessage;
        if ( exception instanceof OAuth2AuthenticationException){
            errorMessage = "이미 가입된 이메일입니다.";
        }else{
            errorMessage = "알 수 없는 이유로 로그인이 안되고 있습니다.";
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");//한글 인코딩 깨지는 문제 방지
        setDefaultFailureUrl("/user/error?error=true&exception=" + errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }


}
