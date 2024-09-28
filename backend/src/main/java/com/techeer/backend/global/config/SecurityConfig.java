package com.techeer.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // 비활성화
            .authorizeHttpRequests(authorize -> authorize
                            .anyRequest().permitAll() //모든 접근 가능
                    //.requestMatchers("/api/v1/login").permitAll()   // 2. api/v1/login 경로는 인증 없이 허용
                    //.requestMatchers("/api/v1/register").permitAll() // api/v1/register 경로는 인증 없이 허용
                    //.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger UI와 API docs 허용
                    //.anyRequest().authenticated() // 3. 그 외의 모든 요청은 인증 필요
            )
            .httpBasic(Customizer.withDefaults()) // 4. HTTP Basic 인증
            .formLogin(Customizer.withDefaults()); // 5. 기본 폼 로그인 사용

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 암호화 방식 사용
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}