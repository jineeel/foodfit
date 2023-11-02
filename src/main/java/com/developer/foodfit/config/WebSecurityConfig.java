package com.developer.foodfit.config;

import com.developer.foodfit.config.oauth.OAuth2LoginFailureHandler;
import com.developer.foodfit.service.PrincipalOauthUserService;
import com.developer.foodfit.service.PrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final PrincipalOauthUserService principalOauthUserService;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/item/**"),
                                        AntPathRequestMatcher.antMatcher("/login"),
                                        AntPathRequestMatcher.antMatcher("/api/signup"),
<<<<<<< HEAD
=======
                                        AntPathRequestMatcher.antMatcher("/board/**"),
>>>>>>> 150101a4079d8781ff4e2a4b21b3cbc62cb4f663
                                        AntPathRequestMatcher.antMatcher("/"),
                                        AntPathRequestMatcher.antMatcher("/user/**"),
                                        AntPathRequestMatcher.antMatcher("/images/**"),
                                        AntPathRequestMatcher.antMatcher("/js/**"),
                                        AntPathRequestMatcher.antMatcher("/plugins/**"),
                                        AntPathRequestMatcher.antMatcher("/styles/**")
                                ).permitAll()
                                .anyRequest().authenticated())
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

