/**
 * @introduce
 * @author Hwang junsik
 */
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // csrf(강제 수행) 비활성화
                .formLogin(form -> form.disable()) // form 로그인 비활성화
                .httpBasic(basic -> basic.disable()) // basic 인증 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/comments/**").authenticated() // 댓글 기능 비활성화
                        .requestMatchers(HttpMethod.POST, "/api/posts").authenticated() // 글 작성 기능 비활성화
                        .requestMatchers(HttpMethod.PUT, "/api/posts").authenticated() // 글 수정 기능 비활성화
                        .requestMatchers(HttpMethod.DELETE, "/api/posts").authenticated() // 글 삭제 기능 비활성화
                        .anyRequest().permitAll() // 나머지 다 활성화(특정 글 보기, 글 목록 보기)
                )
                // 요청시 세션 생성, 저장, 인증 상태 유지
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        System.out.println("authenticationManager bean 생성됨");
        return config.getAuthenticationManager();
    }
}
