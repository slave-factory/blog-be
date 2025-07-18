/**
 * @introduce
 *  - 회원 접근 권한 & 보안 관리<br>
 *
 *  SecurityConfig.java
 *
 * @author Hwang junsik
 */
package com.blog.lastdance.config;

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

    /**
     *
     * <p>http를 build하는 메서드(securityFilterChain)</p>
     * <hr>
     * <p>
     *  - csrf 비활성화<br>
     *  - form 비활성화(react와의 협업을 위해)<br>
     *  - basic 인증 비활성화<br>
     *  - 비회원 집근 제한<br>
     *  - 세션 활용<br>
     *  </p>
     * <hr>
     * @param http spring security 설정을 위한 HttpSecurity
     * @return build한 http를 반환
     * @throws Exception <p>접근 권한이 없는데 접근하는 경우</p> <p>ex) 로그인을 하지 않고 글을 업로드 하려는 경우</p>
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // csrf(강제 수행) 비활성화
                .formLogin(form -> form.disable()) // form 로그인 비활성화
                .httpBasic(basic -> basic.disable()) // basic 인증 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/comments/**").authenticated() // 댓글 기능 비활성화
                        .requestMatchers(HttpMethod.POST, "/api/posts").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/posts").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/posts").authenticated()
                        .anyRequest().permitAll()
                )
                // 요청시 세션 생성, 저장, 인증 상태 유지
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );
        return http.build();
    }

    /**
     * <p>비밀번호를 암호화 하는 메서드</p>
     * @return 비밀번호를 암호화하는 메서드를 가진 클래스
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * <pr>spring security 모든 인증 처리 authenticationmanager</pr>
     * @param config
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
