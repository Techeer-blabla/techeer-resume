package com.techeer.backend.global.oauth;

import com.techeer.backend.api.user.converter.UserConverter;
import com.techeer.backend.api.user.domain.Role;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.repository.UserRepository;
import com.techeer.backend.global.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("OAuth2 Login 성공!");

        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
            String refreshToken = jwtService.createRefreshToken();

            // 이메일로 사용자가 이미 있는지 확인
            userRepository.findByEmail(oAuth2User.getEmail())
                    .ifPresentOrElse(user -> {
                        // 기존 유저는 RefreshToken 업데이트
                        user.updateRefreshToken(refreshToken);
                        userRepository.saveAndFlush(user);

                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");

                        // 로그인 성공 처리
                        String jsonResponse = String.format(
                                "{\"message\": \"login successful\", \"accessToken\": \"%s\", \"refreshToken\": \"%s\"}",
                                accessToken, refreshToken);
                        try {
                            response.getWriter().write(jsonResponse);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }, () -> {
                        // 사용자가 존재하지 않을 경우 (GUEST)
                        // todo: 지금은 바로 REGULAR 저장 하지만 나중에 수정
                        User newUser = UserConverter.fromSignUp(oAuth2User.getEmail(),refreshToken,Role.REGULAR);// 초기 RefreshToken 설정
                        userRepository.save(newUser); // 새로운 사용자 저장

                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");

                        // 회원가입 필요 메시지와 함께 토큰 반환
                        String jsonResponse = String.format(
                                "{\"message\": \"additional signup required\", \"accessToken\": \"%s\", \"refreshToken\": \"%s\"}",
                                jwtService.createAccessToken(newUser.getEmail()), refreshToken);
                        try {
                            response.getWriter().write(jsonResponse);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

        } catch (Exception e) {
            throw e;
        }


    }
}