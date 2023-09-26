package com.developer.foodfit.config;

import com.developer.foodfit.config.oauth.OAuth2LoginFailureHandler;
import com.developer.foodfit.constant.Role;
import com.developer.foodfit.domain.User;
import com.developer.foodfit.dto.PrincipalDetails;
import com.developer.foodfit.service.PrincipalOauthUserService;
import com.developer.foodfit.service.PrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final PrincipalOauthUserService principalOauthUserService;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(crsf -> crsf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/item/**"),
                                        AntPathRequestMatcher.antMatcher("/mypage"), //TODO:추후 삭제
                                        AntPathRequestMatcher.antMatcher("/login"),
                                        AntPathRequestMatcher.antMatcher("/api/**"),
                                        AntPathRequestMatcher.antMatcher("/"),
                                        AntPathRequestMatcher.antMatcher("/user/**"),
                                        AntPathRequestMatcher.antMatcher("/images/**"),
                                        AntPathRequestMatcher.antMatcher("/js/**"),
                                        AntPathRequestMatcher.antMatcher("/plugins/**"),
                                        AntPathRequestMatcher.antMatcher("/styles/**")
                                ).permitAll()
                                .anyRequest().permitAll())
                                //.authenticated())
                .formLogin((form) ->
                        form.loginPage("/login").defaultSuccessUrl("/",true).failureHandler(loginFailHandler()))
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll()
                        .invalidateHttpSession(true))
                .oauth2Login(oauth ->
                        oauth.loginPage("/login").permitAll()
                                .defaultSuccessUrl("/",true)
                                .failureHandler(oAuth2LoginFailureHandler)
                                .userInfoEndpoint(user -> user.userService(principalOauthUserService)));


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       PrincipalService principalService) throws Exception{
        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        sharedObject.userDetailsService(principalService);
        AuthenticationManager authenticationManager = sharedObject.build();
        http.authenticationManager(authenticationManager);

        return authenticationManager;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginFailHandler loginFailHandler(){
        return new LoginFailHandler();
    }



}

